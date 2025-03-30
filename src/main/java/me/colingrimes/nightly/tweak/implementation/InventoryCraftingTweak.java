package me.colingrimes.nightly.tweak.implementation;

import me.colingrimes.midnight.scheduler.Scheduler;
import me.colingrimes.nightly.Nightly;
import me.colingrimes.nightly.config.Settings;
import me.colingrimes.nightly.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class InventoryCraftingTweak extends Tweak {

	public InventoryCraftingTweak(@Nonnull Nightly plugin) {
		super(plugin, "inventory_crafting");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_INVENTORY_CRAFTING.get();
	}

	@EventHandler
	public void onInventoryClick(@Nonnull InventoryClickEvent event) {
		if (event.getView().getTopInventory().getType() != InventoryType.CRAFTING) {
			return;
		}

		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		if (event.getClick().isRightClick() && item != null && item.getType() == Material.CRAFTING_TABLE) {
			event.setCancelled(true);
			Scheduler.sync().run(() -> player.openWorkbench(null, true));
		}
	}
}
