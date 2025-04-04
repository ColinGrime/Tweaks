package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.scheduler.Scheduler;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class TorchThrowTweak extends Tweak {

	public TorchThrowTweak(@Nonnull Tweaks plugin) {
		super(plugin, "torch_throw");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_TORCH_THROW.get();
	}

	@EventHandler
	public void onPlayerDropItem(@Nonnull PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getType() != Material.TORCH) {
			return;
		}

		Item item = event.getItemDrop();
		Scheduler.sync().runRepeating((task) -> {
			if (item.isDead()) {
				task.stop();
			}

			Location location = item.getLocation();
			boolean readyToPlace = Math.abs(item.getVelocity().getY()) < 0.01 && !location.clone().add(0, -1, 0).getBlock().getType().isAir();
			if (!readyToPlace) {
				return;
			}

			task.stop();

			Block block = location.getBlock();
			if (block.getType().isAir()) {
				block.setType(Material.TORCH);

				if (item.getItemStack().getAmount() > 1) {
					ItemStack itemStack = item.getItemStack();
					itemStack.setAmount(itemStack.getAmount() - 1);
					item.setItemStack(itemStack);
				} else {
					item.remove();
				}
			}
		}, 1L, 1L);
	}
}
