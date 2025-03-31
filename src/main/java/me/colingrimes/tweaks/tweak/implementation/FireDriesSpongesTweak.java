package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.event.PlayerInteractBlockEvent;
import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.midnight.util.bukkit.Items;
import me.colingrimes.midnight.util.bukkit.Players;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
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
		return Settings.TWEAK_FIRE_DRIES_SPONGES.get();
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
		Players.sound(player, Sound.BLOCK_FIRE_EXTINGUISH);

		// Damage flint & steel.
		if (event.isItem(Material.FLINT_AND_STEEL) && Items.damage(event.getItem())) {
			Players.sound(player, Sound.ENTITY_ITEM_BREAK);
		}

		// Use a single fire charge.
		if (event.isItem(Material.FIRE_CHARGE) && Inventories.removeSingle(event.getInventory(), event.getItem())) {
			Players.sound(player, Sound.ENTITY_ITEM_BREAK);
		}
	}
}
