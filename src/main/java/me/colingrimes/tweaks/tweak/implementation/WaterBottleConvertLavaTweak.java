package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.scheduler.Scheduler;
import me.colingrimes.midnight.util.bukkit.Locations;
import me.colingrimes.midnight.util.misc.Random;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.SplashPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import javax.annotation.Nonnull;

public class WaterBottleConvertLavaTweak extends Tweak {

	public WaterBottleConvertLavaTweak(@Nonnull Tweaks plugin) {
		super(plugin, "water_bottle_convert_lava");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_WATER_BOTTLE_CONVERT_LAVA.get();
	}

	@EventHandler
	public void onProjectileLaunch(@Nonnull ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof SplashPotion potion)) {
			return;
		}

		ItemStack item = potion.getItem();
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		if (meta == null || meta.getBasePotionType() != PotionType.WATER) {
			return;
		}

		Scheduler.sync().runRepeating((task) -> {
			if (potion.isDead()) {
				task.stop();
			} else if (potion.getLocation().getBlock().getType() == Material.LAVA) {
				convertLava(event.getEntity().getLocation(), 0.50, 0, Random.number(6, 10));
				task.stop();
			}
		}, 1L, 1L);
	}

	/**
	 * Attempts to convert the lava in the given radius to obsidian.
	 *
	 * @param location the location
	 * @param radius the radius around the location to check for lava
	 * @param skipChance chance to skip a lava block
	 * @param remaining how much is left to convert
	 * @return how much is left to convert
	 */
	private int convertLava(@Nonnull Location location, double radius, int skipChance, int remaining) {
		if (remaining == 0 || skipChance >= 100) {
			return remaining;
		}

		Location corner1 = location.clone().add(+radius, +radius, +radius);
		Location corner2 = location.clone().add(-radius, -radius, -radius);
		for (Location check : Locations.between(corner1, corner2)) {
			if (check.getBlock().getType() == Material.LAVA && Random.chance(100 - skipChance)) {
				check.getBlock().setType(Material.OBSIDIAN);
				remaining -= 1;
			}
			if (remaining == 0) {
				return 0;
			}
		}

		return convertLava(location, radius + 0.5, skipChance + 25, remaining);
	}
}
