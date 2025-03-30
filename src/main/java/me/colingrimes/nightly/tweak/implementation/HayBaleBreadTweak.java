package me.colingrimes.nightly.tweak.implementation;

import me.colingrimes.nightly.Nightly;
import me.colingrimes.nightly.config.Settings;
import me.colingrimes.nightly.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class HayBaleBreadTweak extends Tweak {

	public HayBaleBreadTweak(@Nonnull Nightly plugin) {
		super(plugin, "hay_bale_bread");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_HAY_BALE_BREAD.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
			return;
		}

		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		Block block = event.getClickedBlock();
		if (!item.getType().name().endsWith("HOE") || block.getType() != Material.HAY_BLOCK) {
			return;
		}

		block.setType(Material.AIR);
		event.getPlayer().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.BREAD, 3));
		event.setCancelled(true);
	}
}
