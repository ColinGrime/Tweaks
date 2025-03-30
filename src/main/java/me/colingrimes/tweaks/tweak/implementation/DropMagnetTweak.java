package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.util.bukkit.Entities;
import me.colingrimes.midnight.util.bukkit.Vectors;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.List;

public class DropMagnetTweak extends Tweak {

	public DropMagnetTweak(@Nonnull Tweaks plugin) {
		super(plugin, "drops_magnet");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_DROP_MAGNET.get();
	}

	@EventHandler
	public void onBlockDropItem(@Nonnull BlockDropItemEvent event) {
		List<Item> items = event.getItems();
		if (items.isEmpty() || !Entities.nearby(Player.class, items.getFirst().getLocation(), 2).contains(event.getPlayer())) {
			return;
		}

		for (Item item : items) {
			item.setVelocity(Vectors.direction(item, event.getPlayer()).multiply(new Vector(0.2, 0.4, 0.2)));
		}
	}
}
