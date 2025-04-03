package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import javax.annotation.Nonnull;

public class WaterBottleCraftTweak extends Tweak {

	public WaterBottleCraftTweak(@Nonnull Tweaks plugin) {
		super(plugin, "water_bottle_craft");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_WATER_BOTTLE_CRAFT.get();
	}

	@Override
	public void init() {
		ItemStack item = new ItemStack(Material.SPLASH_POTION, Settings.TWEAK_WATER_BOTTLE_CRAFT_AMOUNT.get());
		if (item.getItemMeta() == null || !(item.getItemMeta() instanceof PotionMeta meta)) {
			return;
		}

		meta.setMaxStackSize(Settings.TWEAK_WATER_BOTTLE_CRAFT_AMOUNT.get());
		meta.setBasePotionType(PotionType.WATER);
		item.setItemMeta(meta);

		NamespacedKey key = new NamespacedKey(plugin, "tweaks_water_bottles");
		ShapelessRecipe recipe = new ShapelessRecipe(key, item);
		recipe.addIngredient(Material.WATER_BUCKET);
		Bukkit.addRecipe(recipe);
	}

	@Override
	public void shutdown() {
		Bukkit.removeRecipe(new NamespacedKey(plugin, "tweaks_water_bottles"));
	}

	@EventHandler
	public void onCraftItem(@Nonnull CraftItemEvent event) {
		if (!(event.getRecipe() instanceof ShapelessRecipe recipe) || !recipe.getKey().getKey().equals("tweaks_water_bottles")) {
			return;
		}

		// Remove the whole water bucket.
		CraftingInventory inventory = event.getInventory();
		for (int i=0; i<inventory.getMatrix().length; i++) {
			ItemStack item = inventory.getMatrix()[i];
			if (item != null && item.getType() == Material.WATER_BUCKET) {
				item.setAmount(item.getAmount() - 1);
				break;
			}
		}
	}
}
