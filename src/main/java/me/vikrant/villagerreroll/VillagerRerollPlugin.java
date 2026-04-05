package me.vikrant.villagerreroll;

import me.vikrant.villagerreroll.listener.MerchantClickListener;
import me.vikrant.villagerreroll.listener.VillagerOpenListener;
import me.vikrant.villagerreroll.trades.RerollTrade;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class VillagerRerollPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(
                new VillagerOpenListener(this),
                this
        );

        Bukkit.getPluginManager().registerEvents(
                new MerchantClickListener(this),
                this
        );

        getLogger().info("VillagerReroll enabled");
    }

    @Override
    public void onDisable() {
        // Strip the reroll trade from every loaded villager so it doesn't persist
        // after the plugin is removed (prevents nether_star → emerald exploit).
        int cleaned = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Villager villager : world.getEntitiesByClass(Villager.class)) {
                List<MerchantRecipe> original = villager.getRecipes();
                List<MerchantRecipe> filtered = new ArrayList<>();
                boolean hadReroll = false;

                for (MerchantRecipe recipe : original) {
                    if (RerollTrade.isRerollItem(recipe.getResult())) {
                        hadReroll = true;
                    } else {
                        filtered.add(recipe);
                    }
                }

                if (hadReroll) {
                    villager.setRecipes(filtered);
                    cleaned++;
                }
            }
        }
        getLogger().info("VillagerReroll disabled — stripped reroll trade from " + cleaned + " villager(s).");
    }
}

