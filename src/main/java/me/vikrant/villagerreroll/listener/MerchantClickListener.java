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

public class MerchantClickListener implements Listener {

    @EventHandler
    public void onMerchantClick(InventoryClickEvent event) {

        // Must be merchant inventory
        if (!(event.getInventory() instanceof MerchantInventory merchant))
            return;

        // RESULT SLOT is always raw slot 2
        if (event.getRawSlot() != 2)
            return;

        if (!(event.getWhoClicked() instanceof Player player))
            return;

        MerchantRecipe recipe = merchant.getSelectedRecipe();
        if (recipe == null)
            return;

        if (!RerollTrade.isRerollItem(recipe.getResult()))
            return;

        event.setCancelled(true);

        if (!(merchant.getMerchant() instanceof Villager villager))
            return;

        TradeManager.rerollTrades(villager);

        player.closeInventory();
        player.openMerchant(villager, true);

        player.sendMessage("§aVillager trades rerolled!");
    }
}
