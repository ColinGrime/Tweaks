package me.colingrimes.tweaks.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

	private static final Random random = new Random();

	/**
	 * Turns the string into a color-coded string.
	 *
	 * @param str the string to be colored
	 * @return color-coded string
	 */
	@Nonnull
	public static String color(@Nullable String str) {
		if (str == null || str.isEmpty()) return "";
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	/**
	 * Renames the item with the given name. Supports color codes.
	 *
	 * @param item the item to rename
	 * @param name the name
	 * @return the renamed item
	 */
	@Nonnull
	public static ItemStack rename(@Nonnull ItemStack item, @Nonnull String name) {
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(color(name));
			item.setItemMeta(meta);
		}
		return item;
	}

	/**
	 * Removes a single item from the stack.
	 *
	 * @param item the item stack
	 */
	public static void removeSingle(@Nonnull ItemStack item) {
		item.setAmount(item.getAmount() - 1);
	}

	/**
	 * Damages the specified item by 1 durability.
	 *
	 * @param item the item to damage
	 * @return true if the item was broken
	 */
	public static boolean damage(@Nullable ItemStack item) {
		return damage(item, 1);
	}

	/**
	 * Damages the specified item.
	 *
	 * @param item the item to damage
	 * @param amount the amount to damage the item
	 * @return true if the item was broken
	 */
	public static boolean damage(@Nullable ItemStack item, int amount) {
		if (item == null || !(item.getItemMeta() instanceof Damageable damageable)) {
			return false;
		}

		int damage = damageable.getDamage() + amount;
		if (damage >= item.getType().getMaxDurability()) {
			item.setAmount(item.getAmount() - 1);
			return true;
		} else {
			damageable.setDamage(damage);
			item.setItemMeta(damageable);
			return false;
		}
	}

	/**
	 * Plays the given sound to the given player.
	 *
	 * @param player the player
	 * @param sound  the sound
	 */
	public static void sound(@Nonnull Player player, @Nonnull Sound sound) {
		player.playSound(player.getLocation(), sound, 1F, 1F);
	}

	/**
	 * Retrieves all locations between two locations.
	 *
	 * @param corner1 the first corner
	 * @param corner2 the second corner
	 * @return all blocks between the two locations
	 */
	@Nonnull
	public static List<Location> between(@Nonnull Location corner1, @Nonnull Location corner2) {
		double lowX = Math.min(corner1.getX(), corner2.getX());
		double lowY = Math.min(corner1.getY(), corner2.getY());
		double lowZ = Math.min(corner1.getZ(), corner2.getZ());

		List<Location> locations = new ArrayList<>();
		for (int blockY = Math.abs(corner1.getBlockY() - corner2.getBlockY()); blockY >= 0; blockY--) {
			for (int blockX = 0; blockX < Math.abs(corner1.getBlockX() - corner2.getBlockX()); blockX++) {
				for (int blockZ = 0; blockZ < Math.abs(corner1.getBlockZ() - corner2.getBlockZ()); blockZ++) {
					locations.add(new Location(corner1.getWorld(), lowX + blockX, lowY + blockY, lowZ + blockZ));
				}
			}
		}
		return locations;
	}

	/**
	 * Gets the direction from the source vector to the target vector.
	 *
	 * @param source the source vector
	 * @param target the target vector
	 * @return the resulting unit vector
	 */
	@Nonnull
	public static Vector direction(@Nonnull Vector source, @Nonnull Vector target) {
		return target.subtract(source).normalize();
	}

	/**
	 * Gets the direction from the source location to the target location.
	 *
	 * @param source the source location
	 * @param target the target location
	 * @return the resulting unit vector
	 */
	@Nonnull
	public static Vector direction(@Nonnull Location source, @Nonnull Location target) {
		return direction(source.toVector(), target.toVector());
	}

	/**
	 * Gets the direction from the source entity to the target entity.
	 *
	 * @param source the source entity
	 * @param target the target entity
	 * @return the resulting unit vector
	 */
	@Nonnull
	public static Vector direction(@Nonnull Entity source, @Nonnull Entity target) {
		return direction(source.getLocation(), target.getLocation());
	}

	/**
	 * Gets a random number between two integers.
	 *
	 * @param lowNum  low integer
	 * @param highNum high integer
	 * @return random number
	 */
	public static int number(int lowNum, int highNum) {
		return random.nextInt((highNum - lowNum) + 1) + lowNum;
	}

	/**
	 * Returns true if the random number is higher than a random number 0-100.
	 *
	 * @param num any integer
	 * @return true if num is higher than a random number 0-100
	 */
	public static boolean chance(int num) {
		return num >= number(0, 100);
	}
}
