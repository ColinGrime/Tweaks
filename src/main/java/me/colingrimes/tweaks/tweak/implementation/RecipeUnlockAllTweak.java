package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;

public class RecipeUnlockAllTweak extends Tweak {

	public RecipeUnlockAllTweak(@Nonnull Tweaks plugin) {
		super(plugin, "recipe_unlock_all");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_RECIPE_UNLOCK_ALL.get();
	}

	@EventHandler
	public void onPlayerJoin(@Nonnull PlayerJoinEvent event) {
		event.getPlayer().discoverRecipes(plugin.getAllRecipes());
	}
}
