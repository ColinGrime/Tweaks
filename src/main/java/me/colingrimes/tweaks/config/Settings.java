package me.colingrimes.tweaks.config;

import me.colingrimes.tweaks.Tweaks;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Settings {

	private final List<Option<?>> options = new ArrayList<>();
	public final Option<Boolean>       TWEAK_ANVIL_COLOR                = option("tweaks.anvil-color", true);
	public final Option<Boolean>       TWEAK_ANVIL_REPAIR               = option("tweaks.anvil-repair", true);
	public final Option<Boolean>       TWEAK_CROPS_BONE_MEAL            = option("tweaks.crops-bone-meal", true);
	public final Option<Boolean>       TWEAK_CROPS_TRAMPLE_PROOF        = option("tweaks.crops-trample-proof", true);
	public final Option<Boolean>       TWEAK_DOUBLE_DOORS               = option("tweaks.double-doors.toggle", true);
	public final Option<Boolean>       TWEAK_DOUBLE_DOORS_IRON_DOORS    = option("tweaks.double-doors.iron-doors", false);
	public final Option<Boolean>       TWEAK_DROPS_MAGNET               = option("tweaks.drops-magnet", true);
	public final Option<Boolean>       TWEAK_ENTITY_EQUIP               = option("tweaks.entity-equip", false);
	public final Option<Boolean>       TWEAK_ENTITY_SET_ON_FIRE         = option("tweaks.entity-set-on-fire", true);
	public final Option<Boolean>       TWEAK_FIRE_DRIES_SPONGES         = option("tweaks.fire-dries-sponges", true);
	public final Option<Boolean>       TWEAK_GLASS_BREAK                = option("tweaks.glass-break.toggle", true);
	public final Option<Set<Material>> TWEAK_GLASS_BREAK_MATERIALS      = option("tweaks.glass-break", sec -> sec.getStringList("materials").stream().map(m -> Material.getMaterial(m.toUpperCase())).filter(Objects::nonNull).collect(Collectors.toSet()));
	public final Option<Boolean>       TWEAK_HAY_BALE_BREAD             = option("tweaks.hay-bale-bread", false);
	public final Option<Boolean>       TWEAK_HORSE_STATISTICS           = option("tweaks.horse-statistics.toggle", true);
	public final Option<List<String>>  TWEAK_HORSE_STATISTICS_MESSAGE   = option("tweaks.horse-statistics.message", List.of());
	public final Option<Boolean>       TWEAK_INVENTORY_CRAFTING         = option("tweaks.inventory-crafting", true);
	public final Option<Boolean>       TWEAK_NAME_TAG_DYE               = option("tweaks.name-tag-dye", true);
	public final Option<Boolean>       TWEAK_RECIPE_UNLOCK_ALL          = option("tweaks.recipe-unlock-all", true);
	public final Option<Boolean>       TWEAK_PORTAL_EXPLOSION_PROOF     = option("tweaks.portal-explosion-proof", true);
	public final Option<Boolean>       TWEAK_TORCH_THROW                = option("tweaks.torch-throw", true);
	public final Option<Boolean>       TWEAK_VEHICLE_PICKUP             = option("tweaks.vehicle-pickup", true);
	public final Option<Boolean>       TWEAK_WATER_BOTTLE_CONVERT_LAVA  = option("tweaks.water-bottle-convert-lava", true);
	public final Option<Boolean>       TWEAK_WATER_BOTTLE_CRAFT         = option("tweaks.water-bottle-craft.toggle", true);
	public final Option<Integer>       TWEAK_WATER_BOTTLE_CRAFT_AMOUNT  = option("tweaks.water-bottle-craft.amount", 8);
	public final Option<Boolean>       TWEAK_WEAPON_SWING_THROUGH_GRASS = option("tweaks.weapon-swing-through-grass", true);
	public final Option<Boolean>       TWEAK_XP_FILL                    = option("tweaks.xp-fill.toggle", true);
	public final Option<Integer>       TWEAK_XP_FILL_COST               = option("tweaks.xp-fill.cost", 8);
	public final Option<String>  RELOADED       = option("admin.reloaded", "&2&l✓ &a&lTweaks &ahas been reloaded. Registered &l{amount} &atweaks.");
	public final Option<String>  RESET_RECIPES  = option("reset-recipes", "&2&l✓ &aRecipes have been reset for all online players.");
	public final Option<String>  NO_PERMISSION  = option("no-permission", "&4&l❌ &cYou lack the required permission for this command.");
	public final Option<Boolean> ENABLE_METRICS = option("enable-metrics", true);

	private final Tweaks plugin;

	public Settings(@Nonnull Tweaks plugin) {
		this.plugin = plugin;
		plugin.saveDefaultConfig();
	}

	/**
	 * Reloads the settings, updating the values of all options.
	 */
	public void reload() {
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
		options.forEach(option -> option.reload(config));
	}

	@Nonnull
	private Option<String> option(@Nonnull String path, @Nonnull String def) {
		Option<String> option = new Option<>(config -> config.getString(path, def));
		options.add(option);
		return option;
	}

	@Nonnull
	private Option<List<String>> option(@Nonnull String path, @Nonnull List<String> def) {
		Option<List<String>> option = new Option<>(config -> {
			List<String> value = config.getStringList(path);
			return value.isEmpty() ? def : value;
		});
		options.add(option);
		return option;
	}

	@Nonnull
	private Option<Integer> option(@Nonnull String path, int def) {
		Option<Integer> option = new Option<>(config -> config.getInt(path, def));
		options.add(option);
		return option;
	}

	@Nonnull
	private Option<Boolean> option(@Nonnull String path, boolean def) {
		Option<Boolean> option = new Option<>(config -> config.getBoolean(path, def));
		options.add(option);
		return option;
	}

	@Nonnull
	private <T> Option<T> option(@Nonnull String path, @Nonnull Function<ConfigurationSection, T> extractor) {
		Option<T> option = new Option<>(config -> extractor.apply(config.getConfigurationSection(path)));
		options.add(option);
		return option;
	}
}
