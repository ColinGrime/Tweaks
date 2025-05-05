package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.event.PlayerInteractBlockEvent;
import me.colingrimes.tweaks.tweak.Tweak;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class FireDriesSpongesTweak extends Tweak {

	public FireDriesSpongesTweak(@Nonnull Tweaks plugin) {
		super(plugin, "fire_dries_sponges");
	}

	@Override
	public boolean isEnabled() {
		return settings.TWEAK_FIRE_DRIES_SPONGES.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isRightClick() || !event.isItem(Material.FLINT_AND_STEEL, Material.FIRE_CHARGE) || !event.isBlock(Material.WET_SPONGE)) {
			return;
		}

		event.getBlock().setType(Material.SPONGE);
		event.getBlock().getWorld().spawnParticle(Particle.LARGE_SMOKE, event.getLocation().add(0.5, 0.5, 0.5), 10, 0.3, 0.3, 0.3, 0.01);
		event.setCancelled(true);

		Player player = event.getPlayer();
		Util.sound(player, Sound.BLOCK_FIRE_EXTINGUISH);

		// Damage flint & steel.
		if (event.isItem(Material.FLINT_AND_STEEL) && Util.damage(event.getItem())) {
			Util.sound(player, Sound.ENTITY_ITEM_BREAK);
		}

		// Use a single fire charge.
		if (event.isItem(Material.FIRE_CHARGE)) {
			Util.removeSingle(event.getItem());
		}
	}
}
