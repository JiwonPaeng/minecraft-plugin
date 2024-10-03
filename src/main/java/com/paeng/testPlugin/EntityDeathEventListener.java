package com.paeng.testPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityDeathEventListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity attacker = event.getEntity().getKiller();
        Entity victim = event.getEntity();

        if (attacker == null) return;
        String UUIDString = attacker.getUniqueId().toString();

        LevelManager manager = new LevelManager();
        TestPlugin pluginInstance = JavaPlugin.getPlugin(TestPlugin.class);

        if (victim.getName().equals("Enderman")) {
            if (attacker.getWorld().getName().equals("world_the_end")) manager.addExp(UUIDString, LevelManager.ExpCat.COMBAT, 1);
            else manager.addExp(UUIDString, LevelManager.ExpCat.COMBAT, 350);
        }

        for (LevelManager.ExpCat expcat : LevelManager.ExpCat.values()) {
            manager.addExp(UUIDString, expcat, pluginInstance.getConfig().getInt("Killed." + expcat.getName() + "." + victim.getName()));
        }
    }
}
