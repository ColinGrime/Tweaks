package me.colingrimes.tweaks.command;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class TweakCommand implements CommandExecutor {

	private final Tweaks plugin;

	public TweakCommand(@Nonnull Tweaks plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
		if (!sender.hasPermission("tweaks.admin")) {
			plugin.getSettings().NO_PERMISSION.send(sender);
		} else if (args.length == 1 && args[0].equalsIgnoreCase("resetrecipes")) {
			Bukkit.getOnlinePlayers().forEach(p -> p.undiscoverRecipes(plugin.getAllRecipes()));
			plugin.getSettings().RESET_RECIPES.send(sender);
		} else {
			plugin.getSettings().reload();
			int amount = plugin.registerTweaks();
			sender.sendMessage(Util.color(plugin.getSettings().RELOADED.get().replace("{amount}", String.valueOf(amount))));
		}
		return true;
	}
}
