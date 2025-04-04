package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.event.PlayerInteractBlockEvent;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class DoubleDoorsTweak extends Tweak {

	public DoubleDoorsTweak(@Nonnull Tweaks plugin) {
		super(plugin, "double_doors");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_DOUBLE_DOORS.get();
	}

	@EventHandler
	public void onPlayerInteractBlock(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isRightClick() || !event.getBlockType().name().endsWith("DOOR")) {
			return;
		}

		boolean toggle = !event.isBlock(Material.IRON_DOOR) || Settings.TWEAK_DOUBLE_DOORS_IRON_DOORS.get();

		// Activate the double door.
		if (toggle && activateDoubleDoor(event.getBlock())) {
			event.setCancelled(true);
		} else if (toggle) {
			Door door = (Door) event.getBlock().getBlockData();
			door.setOpen(!door.isOpen());
			event.getBlock().setBlockData(door);
			event.setCancelled(true);
		}

		// Swing hand on iron doors.
		if (event.isBlock(Material.IRON_DOOR) && Settings.TWEAK_DOUBLE_DOORS_IRON_DOORS.get()) {
			event.getPlayer().swingMainHand();
		}
	}

	/**
	 * Attempts to activate double doors.
	 *
	 * @param block the door block
	 * @return true if the double door was activated
	 */
	private boolean activateDoubleDoor(@Nonnull Block block) {
		Door door = (Door) block.getBlockData();
		Block adjacent = block.getRelative(getAdjacentDoorFace(door));
		if (!(adjacent.getBlockData() instanceof Door adjacentDoor)) {
			return false;
		}

		// Verify the adjacent door lines up perfectly with the other door.
		if (door.getHinge() == adjacentDoor.getHinge() || door.getHalf() != adjacentDoor.getHalf()) {
			return false;
		}

		boolean open = !door.isOpen();
		door.setOpen(open);
		block.setBlockData(door);
		adjacentDoor.setOpen(open);
		adjacent.setBlockData(adjacentDoor);
		return true;
	}

	/**
	 * Gets the {@link BlockFace} to the adjacent door.
	 *
	 * @param door the door
	 * @return the adjacent door face
	 */
	@Nonnull
	private BlockFace getAdjacentDoorFace(@Nonnull Door door) {
		BlockFace adjacent = switch (door.getFacing()) {
			case NORTH -> BlockFace.EAST;
			case EAST -> BlockFace.SOUTH;
			case SOUTH -> BlockFace.WEST;
			case WEST -> BlockFace.NORTH;
			default -> BlockFace.SELF;
		};
		return door.getHinge() == Door.Hinge.LEFT ? adjacent : adjacent.getOppositeFace();
	}
}
