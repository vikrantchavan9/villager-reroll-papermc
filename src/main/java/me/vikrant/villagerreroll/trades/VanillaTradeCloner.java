package me.vikrant.villagerreroll.trades;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class VanillaTradeCloner {

    public static List<MerchantRecipe> cloneTrades(Villager source) {

        World world = source.getWorld();
        Location loc = source.getLocation();

        // Spawn temporary villager
        Villager temp = (Villager) world.spawnEntity(loc, EntityType.VILLAGER);

        // Match important properties
        temp.setProfession(source.getProfession());
        temp.setVillagerLevel(source.getVillagerLevel());
        temp.setVillagerType(source.getVillagerType());

        // Force vanilla trade generation
        temp.setVillagerExperience(0);

        List<MerchantRecipe> cloned = new ArrayList<>();
        for (MerchantRecipe recipe : temp.getRecipes()) {
            cloned.add(new MerchantRecipe(
                    recipe.getResult(),
                    recipe.getMaxUses()
            ));
            cloned.get(cloned.size() - 1)
                    .setIngredients(recipe.getIngredients());
        }

        // Clean up immediately
        temp.remove();

        return cloned;
    }
}
