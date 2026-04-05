package me.vikrant.villagerreroll.listener;

import me.vikrant.villagerreroll.trades.RerollTrade;
import me.vikrant.villagerreroll.trades.TradeManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class MerchantClickListener implements Listener {

    private final JavaPlugin plugin;

    public MerchantClickListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMerchantClick(InventoryClickEvent event) {

        // Must be a merchant (villager) inventory
        if (!(event.getInventory() instanceof MerchantInventory merchant))
            return;

        // Result slot is always raw slot 2
        if (event.getRawSlot() != 2)
            return;

        if (!(event.getWhoClicked() instanceof Player player))
            return;

        MerchantRecipe recipe = merchant.getSelectedRecipe();
        if (recipe == null)
            return;

        // Only intercept clicks on our custom reroll trade
        if (!RerollTrade.isRerollItem(recipe.getResult()))
            return;

        // Cancel the click so no item is consumed/given
        event.setCancelled(true);

        if (!(merchant.getMerchant() instanceof Villager villager))
            return;

        // Delegate reroll (handles XP reset, level reset, temp villager, UI reopen)
        TradeManager.rerollTrades(plugin, player, villager);
    }
}
