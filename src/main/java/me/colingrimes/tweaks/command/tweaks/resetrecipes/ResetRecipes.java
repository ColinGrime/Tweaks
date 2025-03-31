package me.colingrimes.tweaks.command.tweaks.resetrecipes;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;
import me.colingrimes.midnight.util.bukkit.Players;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;

import javax.annotation.Nonnull;

public class ResetRecipes implements Command<Tweaks> {

	@Override
	public void execute(@Nonnull Tweaks plugin, @Nonnull Sender sender, @Nonnull ArgumentList args) {
		Players.forEach(p -> p.undiscoverRecipes(plugin.getAllRecipes()));
		Settings.RESET_RECIPES.send(sender);
	}

	@Override
	public void configureProperties(@Nonnull CommandProperties properties) {
		properties.setPermission("tweaks.resetrecipes");
	}
}
