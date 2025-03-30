package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nonnull;

public class WeaponSwingGrassTweak extends Tweak {

	public WeaponSwingGrassTweak(@Nonnull Tweaks plugin) {
		super(plugin, "weapon_swing_grass");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_WEAPON_SWING_GRASS.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
		if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}

		Block block = event.getClickedBlock();
		if (block == null || !block.isPassable()) {
			return;
		}

		Player player = event.getPlayer();
		Material type = player.getInventory().getItemInMainHand().getType();
		if (!type.name().endsWith("_SWORD") && !type.name().endsWith("_AXE")) {
			return;
		}

		Location eye = player.getEyeLocation();
		RayTraceResult result = player.getWorld().rayTrace(eye, eye.getDirection(), 3.5, FluidCollisionMode.NEVER, true, 0, e -> !e.equals(player));
		if (result != null && result.getHitEntity() != null) {
			event.getPlayer().attack(result.getHitEntity());
		}

		event.setCancelled(true);
	}
}
