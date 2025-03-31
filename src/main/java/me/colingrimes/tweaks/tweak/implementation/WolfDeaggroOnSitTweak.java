package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.scheduler.Scheduler;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nonnull;

public class WolfDeaggroOnSitTweak extends Tweak {

	public WolfDeaggroOnSitTweak(@Nonnull Tweaks plugin) {
		super(plugin, "wolf_deaggro_on_sit");
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@EventHandler
	public void onPlayerInteractEntity(@Nonnull PlayerInteractEntityEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || !(event.getRightClicked() instanceof Wolf wolf)) {
			return;
		}

		// Needs to be reworked + consideration towards other wolves who join in the fight.
		// Perhaps shift right click to sit all? Not sure yet.
		if (wolf.isTamed() && event.getPlayer().equals(wolf.getOwner())) {
			boolean wasSitting = wolf.isSitting();
			Scheduler.sync().runLater(() -> {
				if (wolf.isSitting() && !wasSitting) {
					wolf.setAngry(false);
					wolf.setTarget(null);
				}
			}, 2L);
		}
	}
}
