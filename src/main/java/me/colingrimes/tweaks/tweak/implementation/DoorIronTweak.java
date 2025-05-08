package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.event.PlayerInteractBlockEvent;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DoorIronTweak extends Tweak {

	public DoorIronTweak(@Nonnull Tweaks plugin) {
		super(plugin, "doors_iron");
	}

	@Override
	public boolean isEnabled() {
		return settings.TWEAK_DOORS_IRON.get();
	}

	@EventHandler
	public void onPlayerInteractBlock(@Nonnull PlayerInteractBlockEvent event) {
		if (event.isRightClick() && event.isBlock(Material.IRON_DOOR)) {
			event.getPlayer().swingMainHand();
			openDoor(event.getBlock());
		}
	}

	/**
	 * Opens the door that corresponds to the specified block.
	 *
	 * @param block the block
	 */
	private void openDoor(@Nullable Block block) {
		if (block == null || !(block.getBlockData() instanceof Door door)) {
			return;
		}

		door.setOpen(!door.isOpen());
		block.setBlockData(door);
	}
}
