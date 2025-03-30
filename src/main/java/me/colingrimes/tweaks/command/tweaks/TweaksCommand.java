package me.colingrimes.tweaks.command.tweaks;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;
import me.colingrimes.tweaks.Tweaks;

import javax.annotation.Nonnull;

public class TweaksCommand implements Command<Tweaks> {

	@Override
	public void execute(@Nonnull Tweaks plugin, @Nonnull Sender sender, @Nonnull ArgumentList args) {
		plugin.getConfigurationManager().reload();
		plugin.registerTweaks();
	}

	@Override
	public void configureProperties(@Nonnull CommandProperties properties) {
		properties.setPermission("tweaks.reload");
	}
}
