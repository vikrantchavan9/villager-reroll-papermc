package me.vikrant.villagerreroll.trades;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class TradeManager {

    /**
     * Rerolls a villager's trades while preserving profession, brain, and POI state
     * so that leveling continues to work normally after reroll.
     *
     * Root cause fix:
     *  - Never call setProfession(NONE) — that wipes the brain's JOB_SITE memory
     *  - Reset villagerExperience + villagerLevel so XP thresholds re-sync from scratch
     *  - Use a temp villager (read after 1 tick) to get freshly-generated vanilla trades
     */
    public static void rerollTrades(JavaPlugin plugin, Player player, Villager villager) {

        // ── Fix 1: Reset XP and level BEFORE anything else ──────────────────────
        // Without this, the XP counter is out of sync with level thresholds after
        // the profession re-acquired from the job block, which silently breaks leveling.
        villager.setVillagerExperience(0);
        villager.setVillagerLevel(1);

        // ── Fix 2: Close the UI now so it's clean when we reopen ─────────────────
        player.closeInventory();

        // ── Fix 3: Spawn a temp villager to get freshly-generated vanilla trades ──
        // We do NOT call setProfession(NONE) on the REAL villager — that would erase
        // the brain's MemoryModuleType.JOB_SITE and break POI linking after reassign.
        Villager temp = (Villager) villager.getWorld()
                .spawnEntity(villager.getLocation(), EntityType.VILLAGER);

        // Match the real villager's properties so trades are appropriate
        temp.setProfession(villager.getProfession());
        temp.setVillagerType(villager.getVillagerType());
        temp.setVillagerLevel(1);
        temp.setVillagerExperience(0);

        // Prevent the temp villager from interacting with the world
        temp.setAI(false);
        temp.setSilent(true);
        temp.setInvisible(true);

        // ── Fix 4: Read trades after 1 tick ──────────────────────────────────────
        // Vanilla generates trades asynchronously on the next server tick after the
        // entity is created + profession is set. Reading same-tick returns an empty list.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            List<MerchantRecipe> fresh = new ArrayList<>(temp.getRecipes());
            temp.remove(); // always clean up

            if (fresh.isEmpty()) {
                // Fallback: keep whatever trades the real villager currently has
                // (minus any prior reroll trade) so the player always sees something
                fresh = new ArrayList<>();
                for (MerchantRecipe r : villager.getRecipes()) {
                    if (!RerollTrade.isRerollItem(r.getResult())) {
                        fresh.add(r);
                    }
                }
            }

            // Append the reroll trade so the player can reroll again
            fresh.add(RerollTrade.create());
            villager.setRecipes(fresh);

            player.sendMessage("§aVillager trades rerolled!");
            player.openMerchant(villager, true);

        }, 1L);
    }
}
