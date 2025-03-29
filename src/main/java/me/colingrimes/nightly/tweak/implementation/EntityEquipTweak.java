package me.colingrimes.nightly.tweak.implementation;

import me.colingrimes.midnight.scheduler.Scheduler;
import me.colingrimes.midnight.util.bukkit.Entities;
import me.colingrimes.nightly.Nightly;
import me.colingrimes.nightly.config.Settings;
import me.colingrimes.nightly.tweak.Tweak;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EntityEquipment;

import javax.annotation.Nonnull;

public class EntityEquipTweak extends Tweak {

	public EntityEquipTweak(@Nonnull Nightly plugin) {
		super(plugin, "entity_equip");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_ENTITY_EQUIP.get();
	}

	@EventHandler
	public void onPlayerDropItem(@Nonnull PlayerDropItemEvent event) {
		Scheduler.sync().runLater(() -> {
			if (event.getItemDrop().isDead()) {
				return;
			}

			for (LivingEntity entity : Entities.nearby(LivingEntity.class, event.getItemDrop().getLocation(), 1)) {
				EntityEquipment equipment = entity.getEquipment();
				if (equipment == null || entity.getLocation().getWorld() == null) {
					continue;
				}

				Location location = entity.getLocation();
				location.getWorld().dropItemNaturally(location, equipment.getItemInMainHand());
				equipment.setItemInMainHand(event.getItemDrop().getItemStack());
				event.getItemDrop().remove();
			}
		}, 10L);
	}
}
