package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.event.PlayerInteractBlockEvent;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DoorDoubleTweak extends Tweak {

	public DoorDoubleTweak(@Nonnull Tweaks plugin) {
		super(plugin, "doors_double");
	}

	@Override
	public boolean isEnabled() {
		return settings.TWEAK_DOORS_DOUBLE.get();
	}

	@EventHandler
	public void onPlayerInteractBlock(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isRightClick() || !event.getBlockType().name().endsWith("_DOOR")) {
			return;
		}

		// Ignore iron doors if the iron door tweak is off.
		if (event.isBlock(Material.IRON_DOOR) && !settings.TWEAK_DOORS_IRON.get()) {
			return;
		}

		openDoor(getDoubleDoor(event.getBlock()));
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

	/**
	 * Gets the adjacent door block if available.
	 *
	 * @param doorBlock the door block
	 * @return the adjacent door (making up the double door) or null
	 */
	@Nullable
	private Block getDoubleDoor(@Nonnull Block doorBlock) {
		if (!(doorBlock instanceof Door door)) {
			return null;
		}

		Block adjacent = doorBlock.getRelative(getAdjacentDoorFace(door));
		if (!(adjacent.getBlockData() instanceof Door adjacentDoor)) {
			return null;
		}

		// Verify the adjacent door lines up perfectly with the other door.
		if (door.getHinge() == adjacentDoor.getHinge() || door.getHalf() != adjacentDoor.getHalf()) {
			return null;
		}

		return adjacent;
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
