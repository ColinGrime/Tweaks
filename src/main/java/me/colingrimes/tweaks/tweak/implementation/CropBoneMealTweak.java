package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.event.PlayerInteractBlockEvent;
import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

/**
 * Allows you to bone meal a few additional crops:
 * <li>Nether Wart</li>
 * <li>Sugar Cane</li>
 * <li>Cactus</li>
 */
public class CropBoneMealTweak extends Tweak {

	public CropBoneMealTweak(@Nonnull Tweaks plugin) {
		super(plugin, "crops_bone_meal");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_CROPS_BONE_MEAL.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isRightClick() || !event.isItem(Material.BONE_MEAL) || !(event.getBlock().getBlockData() instanceof Ageable crop)) {
			return;
		}

		// Grows height crops.
		if (event.isBlock(Material.SUGAR_CANE, Material.CACTUS)) {
			Location location = event.getLocation();
			while (location.getBlock().getType() == event.getBlockType()) {
				location.add(0, 1, 0);
			}

			Block top = location.getBlock();
			if (top.getType().isAir() && Inventories.removeSingle(event.getInventory(), event.getItem())) {
				top.setType(event.getBlockType());
				event.setCancelled(true);
			}
		}

		// Grows nether wart.
		if (event.isBlock(Material.NETHER_WART) && crop.getAge() < crop.getMaximumAge() && Inventories.removeSingle(event.getInventory(), event.getItem())) {
			crop.setAge(crop.getAge() + 1);
			event.getBlock().setBlockData(crop);
			event.setCancelled(true);
		}
	}
}
