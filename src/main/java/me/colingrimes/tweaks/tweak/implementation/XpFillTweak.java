package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.util.bukkit.Experience;
import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.midnight.util.bukkit.Items;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;

public class XpFillTweak extends Tweak {

	public XpFillTweak(@Nonnull Tweaks plugin) {
		super(plugin, "xp_fill");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_XP_FILL.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
			return;
		}

		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		ItemStack item = inventory.getItemInMainHand();
		if (item.getType() != Material.GLASS_BOTTLE || event.getClickedBlock().getType() != Material.ENCHANTING_TABLE) {
			return;
		}

		if (Experience.fromPlayer(player) >= Settings.TWEAK_XP_FILL_COST.get() && Inventories.removeSingle(inventory, item) == 1) {
			Experience.remove(player, Settings.TWEAK_XP_FILL_COST.get());
			Inventories.give(player, Items.of(Material.EXPERIENCE_BOTTLE).build(), true);
			event.setCancelled(true);
		}
	}
}
