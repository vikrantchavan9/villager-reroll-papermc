package me.vikrant.villagerreroll.trades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RerollTrade {

    public static MerchantRecipe create() {

        ItemStack result = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName("§a🔁 Reroll Trades");
        meta.setLore(List.of(
                "§7Generate fresh vanilla trades",
                "§8Does not consume emerald"
        ));
        result.setItemMeta(meta);

        MerchantRecipe recipe = new MerchantRecipe(result, 9999);

        // ✅ REQUIRED ingredient
        recipe.addIngredient(new ItemStack(Material.EMERALD, 1));

        recipe.setExperienceReward(false);
        recipe.setVillagerExperience(0);
        recipe.setPriceMultiplier(0f);

        return recipe;
    }

    public static boolean isRerollItem(ItemStack item) {
        return item != null
                && item.getType() == Material.NETHER_STAR
                && item.hasItemMeta()
                && item.getItemMeta().getDisplayName().contains("Reroll");
    }
}
