package me.colingrimes.nightly.tweak.implementation;

import me.colingrimes.nightly.Nightly;
import me.colingrimes.nightly.config.Settings;
import me.colingrimes.nightly.tweak.Tweak;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;

import javax.annotation.Nonnull;

public class AnvilTooExpensiveTweak extends Tweak {

	public AnvilTooExpensiveTweak(@Nonnull Nightly plugin) {
		super(plugin, "anvil_too_expensive");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_ANVIL_TOO_EXPENSIVE.get();
	}

	@EventHandler
	public void onPrepareAnvil(@Nonnull PrepareAnvilEvent event) {
		event.getView().setMaximumRepairCost(Integer.MAX_VALUE);
	}
}
