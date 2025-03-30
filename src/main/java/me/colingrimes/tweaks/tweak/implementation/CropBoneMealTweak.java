package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Set;

public class CropBoneMealTweak extends Tweak {

	private final Set<Material> SPECIAL_CROPS = Set.of(Material.SUGAR_CANE, Material.CACTUS);

	public CropBoneMealTweak(@Nonnull Tweaks plugin) {
		super(plugin, "crops_bone_meal");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_CROPS_BONE_MEAL.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
			return;
		}

		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		Block block = event.getClickedBlock();
		if (item.getType() != Material.BONE_MEAL || block == null || !(block.getBlockData() instanceof Ageable crop)) {
			return;
		}

		// Grows special crops.
		Material type = block.getType();
		if (SPECIAL_CROPS.contains(type)) {
			Location location = block.getLocation();
			while (location.add(0, 1, 0).getBlock().getType() == type) {
				crop.setAge(crop.getAge() + 1);
			}

			Block special = location.getBlock();
			if (!special.getType().isAir() || crop.getAge() == crop.getMaximumAge()) {
				return;
			}

			special.setType(type);
			Inventories.removeSingle(event.getPlayer().getInventory(), item);
			event.setCancelled(true);
			return;
		}

		// Grows basic crops.
		if (crop.getAge() != crop.getMaximumAge()) {
			crop.setAge(crop.getAge() + 1);
			block.setBlockData(crop);
			Inventories.removeSingle(event.getPlayer().getInventory(), item);
			event.setCancelled(true);
		}
	}
}
