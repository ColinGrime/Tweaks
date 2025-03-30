package me.colingrimes.nightly.command.nightly;

import me.colingrimes.midnight.command.Command;
import me.colingrimes.midnight.command.handler.util.ArgumentList;
import me.colingrimes.midnight.command.handler.util.CommandProperties;
import me.colingrimes.midnight.command.handler.util.Sender;
import me.colingrimes.nightly.Nightly;

import javax.annotation.Nonnull;

public class NightlyCommand implements Command<Nightly> {

	@Override
	public void execute(@Nonnull Nightly plugin, @Nonnull Sender sender, @Nonnull ArgumentList args) {
		plugin.getConfigurationManager().reload();
		plugin.registerTweaks();
	}

	@Override
	public void configureProperties(@Nonnull CommandProperties properties) {
		properties.setPermission("nightly.reload");
	}
}
