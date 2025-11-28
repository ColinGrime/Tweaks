package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.tweak.Tweak;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class SnowballTweak extends Tweak {

	private static final List<Material> grasses = List.of(
			Material.SHORT_GRASS,
			Material.TALL_GRASS,
			Material.FERN,
			Material.LARGE_FERN,
			Material.DEAD_BUSH
	);

	public SnowballTweak(@Nonnull Tweaks plugin) {
		super(plugin, "snowballs");
	}

	@Override
	public boolean isEnabled() {
		return getCount() > 0;
	}

	@Override
	public int getCount() {
		Boolean[] toggles = {
				settings.TWEAK_SNOWBALLS_ADD_SNOW_LAYER.get(),
				settings.TWEAK_SNOWBALLS_BREAK_PLANTS.get(),
				settings.TWEAK_SNOWBALLS_DAMAGE.get(),
				settings.TWEAK_SNOWBALLS_EXTINGUISH_ENTITIES.get(),
				settings.TWEAK_SNOWBALLS_EXTINGUISH_FIRE.get(),
				settings.TWEAK_SNOWBALLS_FORM_ICE.get(),
				settings.TWEAK_SNOWBALLS_FORM_SNOW.get(),
				settings.TWEAK_SNOWBALLS_KNOCKBACK.get()
		};
		return (int) Arrays.stream(toggles).filter(t -> t).count();
	}

	@EventHandler
	public void onProjectileHit(@Nonnull ProjectileHitEvent event) {
		if (event.getEntity().getType() != EntityType.SNOWBALL) {
			return;
		}

		hitTweaks(event);
		blockTweaks(event);
	}

	private void hitTweaks(@Nonnull ProjectileHitEvent event) {
		if (!(event.getEntity().getShooter() instanceof Entity shooter) || !(event.getHitEntity() instanceof LivingEntity hit)) {
			return;
		}

		// TWEAK -- damage
		if (settings.TWEAK_SNOWBALLS_DAMAGE.get()) {
			hit.setHealth(hit.getHealth() - settings.TWEAK_SNOWBALLS_DAMAGE_AMOUNT.get());
		}

		// TWEAK -- knockback
		if (settings.TWEAK_SNOWBALLS_KNOCKBACK.get()) {
			hit.setVelocity(Util.direction(shooter, hit).multiply(settings.TWEAK_SNOWBALLS_KNOCKBACK_AMOUNT.get()));
		}

		// TWEAK -- extinguish entity
		if (settings.TWEAK_SNOWBALLS_EXTINGUISH_ENTITIES.get() && hit.getFireTicks() > 0) {
			hit.setFireTicks(0);
			hit.getWorld().playSound(hit.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1F, 1F);
		}
	}

	private void blockTweaks(@Nonnull ProjectileHitEvent event) {
		Block hit = event.getHitBlock();
		BlockFace face = event.getHitBlockFace();
		if (hit == null || face == null) {
			return;
		}

		Block target = hit.getRelative(face);
		Block below = target.getRelative(BlockFace.DOWN);
		Material targetType = target.getType();

		// TWEAK -- add snow layer on snow layers
		Block existing = hit.getBlockData() instanceof Snow s && s.getLayers() < s.getMaximumLayers() ? hit : target;
		if (settings.TWEAK_SNOWBALLS_ADD_SNOW_LAYER.get() && existing.getBlockData() instanceof Snow snow && snow.getLayers() < snow.getMaximumLayers()) {
			snow.setLayers(snow.getLayers() + 1);
			existing.setBlockData(snow);
			return;
		}

		// TWEAK -- form snow
		boolean isPlaceableOn = (below.getType().isSolid() && below.getType().isOccluding()) || (below.getBlockData() instanceof Snow snow && snow.getLayers() == snow.getMaximumLayers());
		if (settings.TWEAK_SNOWBALLS_FORM_SNOW.get() && targetType.isAir() && isPlaceableOn) {
			target.setType(Material.SNOW);
			return;
		}

		// TWEAK -- form ice (only on solid block contact)
		if (settings.TWEAK_SNOWBALLS_FORM_ICE.get() && target.getType() == Material.WATER) {
			target.setType(Material.ICE);
			return;
		}

		// TWEAK -- break plants (only on solid block contact)
		boolean isPlant = Tag.FLOWERS.isTagged(targetType) || Tag.SMALL_FLOWERS.isTagged(targetType) || grasses.contains(targetType);
		if (settings.TWEAK_SNOWBALLS_BREAK_PLANTS.get() && isPlant) {
			target.breakNaturally();
			event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.BLOCK_GRASS_BREAK, 1F, 1F);
			return;
		}

		// TWEAK -- extinguish fire
		if (settings.TWEAK_SNOWBALLS_EXTINGUISH_FIRE.get() && Tag.FIRE.isTagged(targetType)) {
			target.setType(Material.AIR);
			event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1F, 1F);
		}
	}

	// NOTE: Due to the ProjectileHitEvent not running on non-solid blocks,
	//       we have to run a timer on snowball throw to detect midair collisions.
	@EventHandler
	public void onProjectileLaunch(@Nonnull ProjectileLaunchEvent event) {
		Entity snowball = event.getEntity();
		if (event.getEntity().getType() != EntityType.SNOWBALL) {
			return;
		}

		Bukkit.getScheduler().runTaskTimer(plugin, (task) -> {
			if (snowball.isDead()) {
				task.cancel();
				return;
			}

			Location location = snowball.getLocation();

			// TWEAK -- form ice (mid-water)
			if (settings.TWEAK_SNOWBALLS_FORM_ICE.get()) {
				for (Location loc : Util.around(location, 0.25)) {
					if (loc.getBlock().getType() == Material.WATER) {
						loc.getBlock().setType(Material.ICE);
						snowball.remove();
						return;
					}
				}
			}

			// TWEAK -- break plants (mid-air)
			if (settings.TWEAK_SNOWBALLS_BREAK_PLANTS.get()) {
				for (Location loc : Util.around(location, 0.5)) {
					Material type = loc.getBlock().getType();
					if (Tag.FLOWERS.isTagged(type) || Tag.SMALL_FLOWERS.isTagged(type) || grasses.contains(type)) {
						loc.getBlock().breakNaturally();
						snowball.getWorld().playSound(loc, Sound.BLOCK_GRASS_BREAK, 1F, 1F);
					}
				}
			}
		}, 1L, 1L);
	}
}
