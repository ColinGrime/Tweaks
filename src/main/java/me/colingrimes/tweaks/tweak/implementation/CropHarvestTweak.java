package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.event.PlayerInteractBlockEvent;
import me.colingrimes.tweaks.tweak.Tweak;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class CropHarvestTweak extends Tweak {

	public CropHarvestTweak(@Nonnull Tweaks plugin) {
		super(plugin, "crops_harvest");
	}

	@Override
	public boolean isEnabled() {
		return settings.TWEAK_CROPS_HARVEST.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		Block block = event.getBlock();
		if (!event.isRightClick() || !event.getItem().getType().name().endsWith("HOE") || !(block.getBlockData() instanceof Ageable crop) || crop.getAge() < crop.getMaximumAge()) {
			return;
		}

		block.breakNaturally(event.getItem());
		if (Util.damage(event.getItem())) {
			Util.sound(event.getPlayer(), Sound.ENTITY_ITEM_BREAK);
		}

		// Replant crop.
		crop.setAge(0);
		block.setBlockData(crop);
	}
}
