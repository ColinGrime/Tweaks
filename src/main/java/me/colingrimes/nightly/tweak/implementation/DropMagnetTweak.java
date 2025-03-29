package me.colingrimes.nightly.tweak.implementation;

import me.colingrimes.midnight.util.bukkit.Entities;
import me.colingrimes.midnight.util.bukkit.Vectors;
import me.colingrimes.nightly.Nightly;
import me.colingrimes.nightly.config.Settings;
import me.colingrimes.nightly.tweak.Tweak;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

public class DropMagnetTweak extends Tweak {

	public DropMagnetTweak(@Nonnull Nightly plugin) {
		super(plugin, "drops_magnet");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_DROP_MAGNET.get();
	}

	@EventHandler
	public void onBlockDropItem(@Nonnull BlockDropItemEvent event) {
		if (!Entities.nearby(Player.class, event.getItems().getFirst().getLocation(), 2).contains(event.getPlayer())) {
			return;
		}

		for (Item item : event.getItems()) {
			item.setVelocity(Vectors.direction(item, event.getPlayer()).multiply(new Vector(0.2, 0.4, 0.2)));
		}
	}
}
