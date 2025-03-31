package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.event.PlayerInteractBlockEvent;
import me.colingrimes.midnight.util.bukkit.Players;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class GlassBreakTweak extends Tweak {

	public GlassBreakTweak(@Nonnull Tweaks plugin) {
		super(plugin, "glass_break");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_GLASS_BREAK.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isLeftClick() || !event.getBlockType().name().contains("GLASS")) {
			return;
		}

		if (Settings.TWEAK_GLASS_BREAK_MATERIALS.get().contains(event.getItemType())) {
			Players.sound(event.getPlayer(), Sound.BLOCK_GLASS_BREAK);
			event.getPlayer().breakBlock(event.getBlock());
			event.setCancelled(true);
		}
	}
}
