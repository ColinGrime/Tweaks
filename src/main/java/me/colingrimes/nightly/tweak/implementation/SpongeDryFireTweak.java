package me.colingrimes.nightly.tweak.implementation;

import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.midnight.util.bukkit.Players;
import me.colingrimes.nightly.Nightly;
import me.colingrimes.nightly.config.Settings;
import me.colingrimes.nightly.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;

import javax.annotation.Nonnull;

public class SpongeDryFireTweak extends Tweak {

	public SpongeDryFireTweak(@Nonnull Nightly plugin) {
		super(plugin, "sponge_dry_fire");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_SPONGE_DRY_FIRE.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
			return;
		}

		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack item = inventory.getItemInMainHand();
		Block block = event.getClickedBlock();
		if (item.getType() != Material.FLINT_AND_STEEL && item.getType() != Material.FIRE_CHARGE || block.getType() != Material.WET_SPONGE) {
			return;
		}

		block.setType(Material.SPONGE);
		block.getWorld().spawnParticle(Particle.LARGE_SMOKE, block.getLocation().add(0.5, 0.5, 0.5), 10, 0.3, 0.3, 0.3, 0.01);
		Players.sound(event.getPlayer(), Sound.BLOCK_FIRE_EXTINGUISH);
		event.setCancelled(true);

		// Damage flint & steel.
		if (item.getItemMeta() instanceof Damageable damageable) {
			int damage = damageable.getDamage() + 1;
			if (damage >= item.getType().getMaxDurability()) {
				item.setAmount(0);
			} else {
				damageable.setDamage(damage);
				item.setItemMeta(damageable);
			}
		}

		// Use a single fire charge.
		if (item.getType() == Material.FIRE_CHARGE) {
			Inventories.removeSingle(inventory, item);
		}
	}
}
