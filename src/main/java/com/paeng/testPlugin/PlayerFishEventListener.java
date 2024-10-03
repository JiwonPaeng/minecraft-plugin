package com.paeng.testPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishEventListener implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;

        LevelManager manager = new LevelManager();
        manager.addExp(event.getPlayer().getUniqueId().toString(), LevelManager.ExpCat.FISHING, 100);
    }
}
