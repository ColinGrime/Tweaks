package me.colingrimes.tweaks.config;

import me.colingrimes.midnight.config.annotation.Configuration;
import me.colingrimes.midnight.config.option.Option;
import me.colingrimes.midnight.message.Message;
import org.bukkit.Material;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static me.colingrimes.midnight.config.option.OptionFactory.message;
import static me.colingrimes.midnight.config.option.OptionFactory.option;

@Configuration
public interface Settings {

	Option<Boolean>       TWEAK_ANVIL_COLOR                = option("tweaks.anvil-color", true);
	Option<Boolean>       TWEAK_ANVIL_REPAIR               = option("tweaks.anvil-repair", true);
	Option<Boolean>       TWEAK_CROPS_BONE_MEAL            = option("tweaks.crops-bone-meal", true);
	Option<Boolean>       TWEAK_CROPS_TRAMPLE_PROOF        = option("tweaks.crops-trample-proof", true);
	Option<Boolean>       TWEAK_DROPS_MAGNET               = option("tweaks.drops-magnet", true);
	Option<Boolean>       TWEAK_ENTITY_EQUIP               = option("tweaks.entity-equip", true);
	Option<Boolean>       TWEAK_FIRE_DRIES_SPONGES         = option("tweaks.fire-dries-sponges", true);
	Option<Boolean>       TWEAK_GLASS_BREAK                = option("tweaks.glass-break.toggle", true);
	Option<Set<Material>> TWEAK_GLASS_BREAK_MATERIALS      = option("tweaks.glass-break", sec -> sec.getStringList("materials").stream().map(m -> Material.getMaterial(m.toUpperCase())).filter(Objects::nonNull).collect(Collectors.toSet()));
	Option<Boolean>       TWEAK_HAY_BALE_BREAD             = option("tweaks.hay-bale-bread", false);
	Option<Boolean>       TWEAK_HORSE_STATISTICS           = option("tweaks.horse-statistics.toggle", true);
	     Message<?>       TWEAK_HORSE_STATISTICS_MESSAGE  = message("tweaks.horse-statistics.message");
	Option<Boolean>       TWEAK_INVENTORY_CRAFTING         = option("tweaks.inventory-crafting", true);
	Option<Boolean>       TWEAK_NAME_TAG_DYE               = option("tweaks.name-tag-dye", true);
	Option<Boolean>       TWEAK_RECIPE_UNLOCK_ALL          = option("tweaks.recipe-unlock-all", true);
	Option<Boolean>       TWEAK_PORTAL_EXPLOSION_PROOF     = option("tweaks.portal-explosion-proof", true);
	Option<Boolean>       TWEAK_VEHICLE_PICKUP             = option("tweaks.vehicle-pickup", true);
	Option<Boolean>       TWEAK_WEAPON_SWING_THROUGH_GRASS = option("tweaks.weapon-swing-through-grass", true);
	Option<Boolean>       TWEAK_WOLF_DEAGGRO_ON_SIT        = option("tweaks.wolf-deaggro-on-sit", true);
	Option<Boolean>       TWEAK_XP_FILL                    = option("tweaks.xp-fill.toggle", true);
	Option<Integer>       TWEAK_XP_FILL_COST               = option("tweaks.xp-fill.cost", 8);

	// Admin stuff.
	Message<?> RELOADED      = message("admin.reloaded", "&2&l✓ &a&lTweaks &ahas been reloaded. Registered &l{amount} &atweaks.");
	Message<?> RESET_RECIPES = message("reset-recipes", "&2&l✓ &aRecipes have been reset for all online players.");

	// Misc stuff.
	Option<Boolean> ENABLE_METRICS = option("enable-metrics", true);
}
