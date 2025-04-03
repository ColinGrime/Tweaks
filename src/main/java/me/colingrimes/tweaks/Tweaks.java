package me.colingrimes.tweaks;

import me.colingrimes.midnight.Midnight;
import me.colingrimes.midnight.libs.bstats.bukkit.Metrics;
import me.colingrimes.midnight.util.io.Introspector;
import me.colingrimes.midnight.util.io.Logger;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Recipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tweaks extends Midnight {

	private final List<Tweak> tweaks = new ArrayList<>();
	private List<NamespacedKey> allRecipes;

	@Override
	protected void enable() {
		registerTweaks();

		// Check for Metrics.
		if (Settings.ENABLE_METRICS.get()) {
			new Metrics(this, 25315);
		}
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
		// Clean up.
		HandlerList.unregisterAll(this);
		tweaks.forEach(Tweak::shutdown);
		tweaks.clear();

		// Registration.
		List<Class<?>> classes = Introspector.getClasses(getClassLoader(), getRootPackage() + ".tweak.implementation");
		List<Tweak> tweakClasses = Introspector.instantiateClasses(classes, Tweak.class, this);
		tweaks.addAll(tweakClasses.stream().filter(Tweak::isEnabled).toList());
		tweaks.forEach(Tweak::init);
		Logger.log("Registered " + tweaks.size() + " tweaks.");
		return tweaks.size();
	}

	/**
	 * Gets a list of all recipes in Minecraft.
	 *
	 * @return a list of all recipes
	 */
	@Nonnull
	public List<NamespacedKey> getAllRecipes() {
		if (allRecipes != null) {
			return allRecipes;
		}

		List<NamespacedKey> recipes = new ArrayList<>();
		Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
		while (recipeIterator.hasNext()) {
			Recipe recipe = recipeIterator.next();
			if (recipe instanceof Keyed keyed) {
				recipes.add(keyed.getKey());
			}
		}
		allRecipes = recipes;
		return recipes;
	}
}
