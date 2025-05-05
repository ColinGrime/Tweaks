package me.colingrimes.tweaks.util;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Util {

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
	 * Plays the given sound to the given player.
	 *
	 * @param player the player
	 * @param sound  the sound
	 */
	public static void sound(@Nonnull Player player, @Nonnull Sound sound) {
		player.playSound(player.getLocation(), sound, 1F, 1F);
	}
}
