package me.colingrimes.tweaks.tweak;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public abstract class Tweak implements Listener {

	protected final Tweaks plugin;
	protected final Settings settings;
	protected final String id;

	public Tweak(@Nonnull Tweaks plugin, @Nonnull String id) {
		this.plugin = plugin;
		this.settings = plugin.getSettings();
		this.id = id;
		if (isEnabled()) {
			Bukkit.getPluginManager().registerEvents(this, plugin);
		}
	}

	/**
	 * Gets whether the tweak is enabled.
	 *
	 * @return true if the tweak is enabled
	 */
	public abstract boolean isEnabled();

	/**
	 * Runs when the tweak is initialized.
	 */
	public void init() {}

	/**
	 * Runs when the tweak is shutdown.
	 */
	public void shutdown() {}
}
