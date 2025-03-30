package me.colingrimes.tweaks;

import me.colingrimes.midnight.Midnight;
import me.colingrimes.midnight.util.io.Introspector;
import me.colingrimes.midnight.util.io.Logger;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Tweaks extends Midnight {

	private final List<Tweak> tweaks = new ArrayList<>();

	@Override
	protected void enable() {
		registerTweaks();
	}

	/**
	 * Gets all the enabled tweaks.
	 *
	 * @return the list of enabled tweaks
	 */
	@Nonnull
	public List<Tweak> getTweaks() {
		return tweaks;
	}

	/**
	 * Registers all the tweaks.
	 */
	public int registerTweaks() {
		HandlerList.unregisterAll(this);
		List<Class<?>> classes = Introspector.getClasses(getClassLoader(), getRootPackage() + ".tweak.implementation");
		List<Tweak> tweakClasses = Introspector.instantiateClasses(classes, Tweak.class, this);
		tweaks.clear();
		tweaks.addAll(tweakClasses.stream().filter(Tweak::isEnabled).toList());
		Logger.log("Registered " + tweaks.size() + " tweaks.");
		return tweaks.size();
	}
}
