package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.scheduler.Scheduler;
import me.colingrimes.midnight.util.bukkit.Entities;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EntityEquipment;

import javax.annotation.Nonnull;
import java.util.Set;

public class EntityEquipTweak extends Tweak {

	private final Set<EntityType> EQUIPPABLE_ENTITIES = Set.of(
			EntityType.ZOMBIE,
			EntityType.SKELETON,
			EntityType.HUSK,
			EntityType.STRAY,
			EntityType.DROWNED,
			EntityType.WITHER_SKELETON,
			EntityType.PILLAGER,
			EntityType.PIGLIN,
			EntityType.PIGLIN_BRUTE,
			EntityType.ZOMBIFIED_PIGLIN,
			EntityType.ARMOR_STAND
	);

	public EntityEquipTweak(@Nonnull Tweaks plugin) {
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
				if (!EQUIPPABLE_ENTITIES.contains(entity.getType())) {
					continue;
				}

				EntityEquipment equipment = entity.getEquipment();
				if (equipment == null || entity.getLocation().getWorld() == null) {
					continue;
				}

				Location location = entity.getLocation();
				location.getWorld().dropItemNaturally(location, equipment.getItemInMainHand());
				equipment.setItemInMainHand(event.getItemDrop().getItemStack());
				event.getItemDrop().remove();
				return;
			}
		}, 10L);
	}
}
