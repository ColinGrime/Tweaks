package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.event.PlayerInteractBlockEvent;
import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.midnight.util.bukkit.Players;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
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
		return Settings.TWEAK_ANVIL_REPAIR.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isRightClick() || !event.isItem(Material.IRON_BLOCK) || !event.isBlock(Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL)) {
			return;
		}

		if (Inventories.removeSingle(event.getInventory(), event.getItem())) {
			Block block = event.getBlock();
			BlockFace blockFace = ((Directional) event.getBlock().getBlockData()).getFacing();
			block.setType(block.getType() == Material.CHIPPED_ANVIL ? Material.ANVIL : Material.CHIPPED_ANVIL);

			Directional blockData = (Directional) event.getBlock().getBlockData();
			blockData.setFacing(blockFace);
			block.setBlockData(blockData);

			Players.sound(event.getPlayer(), Sound.BLOCK_ANVIL_USE);
			event.setCancelled(true);
		}
	}
}
