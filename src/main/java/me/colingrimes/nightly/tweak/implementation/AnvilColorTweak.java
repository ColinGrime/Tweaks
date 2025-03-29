package me.colingrimes.nightly.tweak.implementation;

import me.colingrimes.midnight.util.bukkit.Items;
import me.colingrimes.nightly.Nightly;
import me.colingrimes.nightly.config.Settings;
import me.colingrimes.nightly.tweak.Tweak;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;

import javax.annotation.Nonnull;

public class AnvilColorTweak extends Tweak {

	public AnvilColorTweak(@Nonnull Nightly plugin) {
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
