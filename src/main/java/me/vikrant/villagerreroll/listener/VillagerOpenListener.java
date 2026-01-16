package me.vikrant.villagerreroll.listener;

import me.vikrant.villagerreroll.trades.RerollTrade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class VillagerOpenListener implements Listener {

    private final JavaPlugin plugin;

    public VillagerOpenListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onVillagerOpen(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked() instanceof Villager villager)) return;

        Player player = event.getPlayer();

        // ✅ COPY the recipe list (CRITICAL FIX)
        List<MerchantRecipe> recipes =
                new ArrayList<>(villager.getRecipes());

        boolean hasReroll = recipes.stream()
                .anyMatch(r -> RerollTrade.isRerollItem(r.getResult()));

        if (hasReroll) return;

        recipes.add(RerollTrade.create());
        villager.setRecipes(recipes);

        // Force UI refresh next tick
        Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> player.openMerchant(villager, true),
                1L
        );
    }
}
