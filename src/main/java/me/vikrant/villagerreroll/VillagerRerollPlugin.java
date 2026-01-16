package me.vikrant.villagerreroll;

import me.vikrant.villagerreroll.listener.MerchantClickListener;
import me.vikrant.villagerreroll.listener.VillagerOpenListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class VillagerRerollPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(
                new VillagerOpenListener(this),
                this
        );

        Bukkit.getPluginManager().registerEvents(
                new MerchantClickListener(),
                this
        );

        getLogger().info("VillagerReroll enabled");
    }
}
