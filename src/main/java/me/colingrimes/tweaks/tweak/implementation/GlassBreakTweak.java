package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.event.PlayerInteractBlockEvent;
import me.colingrimes.tweaks.tweak.Tweak;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class GlassBreakTweak extends Tweak {

	public GlassBreakTweak(@Nonnull Tweaks plugin) {
		super(plugin, "glass_break");
	}

	@Override
	public boolean isEnabled() {
		return settings.TWEAK_GLASS_BREAK.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isLeftClick() || !event.getBlockType().name().contains("GLASS")) {
			return;
		}

		if (settings.TWEAK_GLASS_BREAK_MATERIALS.get().contains(event.getItemType())) {
			Util.sound(event.getPlayer(), Sound.BLOCK_GLASS_BREAK);
			event.getPlayer().breakBlock(event.getBlock());
			event.setCancelled(true);
		}
	}
}
