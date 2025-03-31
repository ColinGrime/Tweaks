package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.midnight.event.PlayerInteractBlockEvent;
import me.colingrimes.midnight.util.bukkit.Experience;
import me.colingrimes.midnight.util.bukkit.Inventories;
import me.colingrimes.midnight.util.bukkit.Items;
import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.config.Settings;
import me.colingrimes.tweaks.tweak.Tweak;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public class XpFillTweak extends Tweak {

	public XpFillTweak(@Nonnull Tweaks plugin) {
		super(plugin, "xp_fill");
	}

	@Override
	public boolean isEnabled() {
		return Settings.TWEAK_XP_FILL.get();
	}

	@EventHandler
	public void onPlayerInteract(@Nonnull PlayerInteractBlockEvent event) {
		if (!event.isRightClick() || !event.isItem(Material.GLASS_BOTTLE) || !event.isBlock(Material.ENCHANTING_TABLE)) {
			return;
		}

		Player player = event.getPlayer();
		if (Experience.fromPlayer(player) >= Settings.TWEAK_XP_FILL_COST.get() && Inventories.removeSingle(event.getInventory(), event.getItem())) {
			Experience.remove(player, Settings.TWEAK_XP_FILL_COST.get());
			Inventories.give(player, Items.of(Material.EXPERIENCE_BOTTLE).build(), true);
			event.setCancelled(true);
		}
	}
}
