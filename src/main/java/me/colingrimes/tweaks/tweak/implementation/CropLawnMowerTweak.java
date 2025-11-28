package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.tweak.Tweak;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class CropLawnMowerTweak extends Tweak {

	private static final List<Material> grasses = List.of(
			Material.SHORT_GRASS,
			Material.TALL_GRASS,
			Material.FERN,
			Material.LARGE_FERN,
			Material.DEAD_BUSH
	);

	public CropLawnMowerTweak(@Nonnull Tweaks plugin) {
		super(plugin, "crops_lawn_mower");
	}

	@Override
	public boolean isEnabled() {
		return settings.TWEAK_CROPS_LAWN_MOWER.get();
	}

	@EventHandler
	public void onBlockBreak(@Nonnull BlockBreakEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (!Tag.ITEMS_HOES.isTagged(item.getType())) {
			return;
		}

		Block block = event.getBlock();
		if (!Tag.FLOWERS.isTagged(block.getType()) && !Tag.SMALL_FLOWERS.isTagged(block.getType()) && !grasses.contains(block.getType())) {
			return;
		}

		int radius = item.getType() == Material.NETHERITE_HOE ? 2 : 1;
		for (Location loc : Util.around(block.getLocation(), radius)) {
			Material type = loc.getBlock().getType();
			if (Tag.FLOWERS.isTagged(type) || Tag.SMALL_FLOWERS.isTagged(type) || grasses.contains(type)) {
				loc.getBlock().breakNaturally(item);
			}
		}

		event.setCancelled(true);
	}
}
