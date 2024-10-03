package com.paeng.testPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // register events
        getServer().getPluginManager().registerEvents(new EntityDeathEventListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFishEventListener(), this);
        getServer().getPluginManager().registerEvents(this, this);

        // load level data
        LevelManager manager = new LevelManager();
        manager.startUp();
        manager.loadLevelData();
    }

    @Override
    public void onDisable() {
        // unload level data
        LevelManager manager = new LevelManager();
        manager.saveLevelData();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // add new data in the hashmap if not already available
        LevelManager manager = new LevelManager();
        manager.addNewPlayerData(event.getPlayer().getUniqueId().toString());
    }
}