package me.colingrimes.nightly.tweak;

import me.colingrimes.midnight.util.Common;
import me.colingrimes.nightly.Nightly;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public abstract class Tweak implements Listener {

	protected final Nightly plugin;
	protected final String id;

	public Tweak(@Nonnull Nightly plugin, @Nonnull String id) {
		this.plugin = plugin;
		this.id = id;
		if (isEnabled()) {
			Common.register(plugin, this);
		}
	}

	/**
	 * Gets whether the tweak is enabled.
	 *
	 * @return true if the tweak is enabled
	 */
	public abstract boolean isEnabled();
}
