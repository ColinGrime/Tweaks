package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.tweak.Tweak;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.entity.Item;
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
		return settings.TWEAK_DROPS_MAGNET.get();
	}

	@EventHandler
	public void onBlockDropItem(@Nonnull BlockDropItemEvent event) {
		List<Item> items = event.getItems();
		if (!items.isEmpty() && items.getFirst().getNearbyEntities(2, 2, 2).contains(event.getPlayer())) {
			items.forEach(i -> i.setVelocity(Util.direction(i, event.getPlayer()).multiply(new Vector(0.2, 0.4, 0.2))));
		}
	}
}
