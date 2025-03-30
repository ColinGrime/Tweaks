package me.colingrimes.nightly.config;

import me.colingrimes.midnight.config.annotation.Configuration;
import me.colingrimes.midnight.config.option.Option;

import static me.colingrimes.midnight.config.option.OptionFactory.option;

@Configuration
public interface Settings {

	Option<Boolean> TWEAK_ANVIL_COLOR = option("tweaks.anvil-color", true);
	Option<Boolean> TWEAK_ANVIL_TOO_EXPENSIVE = option("tweaks.anvil-too-expensive", true);
	Option<Boolean> TWEAK_CROPS_BONE_MEAL = option("tweaks.crops-bone-meal", true);
	Option<Boolean> TWEAK_CROPS_TRAMPLE_PROOF = option("tweaks.crops-trample-proof", true);
	Option<Boolean> TWEAK_DROP_MAGNET = option("tweaks.drop-magnet", true);
	Option<Boolean> TWEAK_ENTITY_EQUIP = option("tweaks.entity-equip", true);
	Option<Boolean> TWEAK_HAY_BALE_BREAD = option("tweaks.hay-bale-bread", false);
	Option<Boolean> TWEAK_SPONGE_DRY_FIRE = option("tweaks.sponge-dry-fire", true);
	Option<Boolean> TWEAK_NAME_TAG_DYE = option("tweaks.name-tag-dye", true);
	Option<Boolean> TWEAK_VEHICLE_PICKUP = option("tweaks.name-tag-equip", true);
	Option<Boolean> TWEAK_WEAPON_SWING_GRASS = option("tweaks.weapon-swing-grass", true);
	Option<Boolean> TWEAK_XP_FILL = option("tweaks.xp-fill.toggle", true);
	Option<Integer> TWEAK_XP_FILL_COST = option("tweaks.xp-fill.cost", 8);
}
