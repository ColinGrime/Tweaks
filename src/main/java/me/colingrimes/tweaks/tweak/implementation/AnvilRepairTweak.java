package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.event.PlayerInteractBlockEvent;
import me.colingrimes.tweaks.tweak.Tweak;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class AnvilRepairTweak extends Tweak {

	public AnvilRepairTweak(@Nonnull Tweaks plugin) {
		super(plugin, "anvil_repair");
	}

	@Override
	public boolean isEnabled() {
		return settings.TWEAK_ANVIL_REPAIR.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isRightClick() || !event.isItem(Material.IRON_BLOCK) || !event.isBlock(Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL)) {
			return;
		}

		Block block = event.getBlock();
		BlockFace blockFace = ((Directional) event.getBlock().getBlockData()).getFacing();
		block.setType(block.getType() == Material.CHIPPED_ANVIL ? Material.ANVIL : Material.CHIPPED_ANVIL);

		Directional blockData = (Directional) event.getBlock().getBlockData();
		blockData.setFacing(blockFace);
		block.setBlockData(blockData);

		Util.removeSingle(event.getItem());
		Util.sound(event.getPlayer(), Sound.BLOCK_ANVIL_USE);
		event.setCancelled(true);
	}
}
