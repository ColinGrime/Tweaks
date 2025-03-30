package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.util.bukkit.Items;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;

import javax.annotation.Nonnull;

public class AnvilColorTweak extends Tweak {

	public AnvilColorTweak(@Nonnull Tweaks plugin) {
		super(plugin, "anvil_color");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_ANVIL_COLOR.get();
	}

	@EventHandler
	public void onPrepareAnvil(@Nonnull PrepareAnvilEvent event) {
		if (event.getResult() != null) {
			event.setResult(Items.of(event.getResult()).build());
		}
	}
}
