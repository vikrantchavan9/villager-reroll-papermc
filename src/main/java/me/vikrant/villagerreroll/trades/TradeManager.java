package me.vikrant.villagerreroll.trades;

import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;

public class TradeManager {

    public static void rerollTrades(Villager villager) {

        // Clone vanilla trades
        List<MerchantRecipe> trades =
                VanillaTradeCloner.cloneTrades(villager);

        // Append fake reroll trade at bottom
        trades.add(RerollTrade.create());

        villager.setRecipes(trades);
    }
}
