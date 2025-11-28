package me.colingrimes.tweaks.tweak.implementation;

import me.colingrimes.tweaks.Tweaks;
import me.colingrimes.tweaks.tweak.Tweak;

import javax.annotation.Nonnull;

// Used for testing. Should be disabled on prod.
public class TestTweak extends Tweak {

	public TestTweak(@Nonnull Tweaks plugin) {
		super(plugin, "test_tweak");
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

//	@EventHandler
//	public void onBed(PlayerBedEnterEvent event) {
//		if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_NOW) {
//			event.getPlayer().sleep(event.getBed().getLocation(), true);
//		}
//	}
}
