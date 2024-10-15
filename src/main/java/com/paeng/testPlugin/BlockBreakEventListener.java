package com.paeng.testPlugin;

import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockBreakEventListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material block = event.getBlock().getType();
        Player player = event.getPlayer();

        LevelManager manager = new LevelManager();
        TestPlugin pluginInstance = JavaPlugin.getPlugin(TestPlugin.class);

        if (block == Material.WHEAT) {
            Ageable ageable = (Ageable) event.getBlock().getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge()) manager.addExp(player, LevelManager.ExpCat.FARMING, 110);
        }
        else if (block == Material.CARROTS) {
            Ageable ageable = (Ageable) event.getBlock().getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge()) manager.addExp(player, LevelManager.ExpCat.FARMING, 78);
        }
        else if (block == Material.POTATOES) {
            Ageable ageable = (Ageable) event.getBlock().getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge()) manager.addExp(player, LevelManager.ExpCat.FARMING, 78);
        }
        else if (block == Material.NETHER_WART) {
            Ageable ageable = (Ageable) event.getBlock().getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge()) manager.addExp(player, LevelManager.ExpCat.FARMING, 85);
        }
        else if (block == Material.SUGAR_CANE) {
            Ageable ageable = (Ageable) event.getBlock().getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge()) manager.addExp(player, LevelManager.ExpCat.FARMING, 85);
        }

        /*
        Original Data From CosmicSurvival

        Material.SUGAR_CANE -> {
            var baseheight = event.block.location.blockY.toDouble()
            var i = 1
            while (baseheight < 255) {
                baseheight++
                val loc = Location(
                    event.block.world,
                    event.block.location.blockX.toDouble(),
                    baseheight,
                    event.block.location.blockZ.toDouble()
                )
                if (loc.block.type == Material.SUGAR_CANE) {
                    if (CoreProtect.blockLookup(loc.block, 150).size == 0) { i++ }
                }
                else{ break }
            }
            lh.addExp(event.player, 4, 30*min(i,3))
        }
        Material.BAMBOO -> {
            var baseheight = event.block.location.blockY.toDouble()
            var i = 1
            while (baseheight < 255) {
                baseheight++
                val loc = Location(
                    event.block.world,
                    event.block.location.blockX.toDouble(),
                    baseheight,
                    event.block.location.blockZ.toDouble()
                )
                if (loc.block.type == Material.BAMBOO) {
                    if (CoreProtect.blockLookup(loc.block, 150).size == 0) { i++ }
                }
                else{ break }
            }
            lh.addExp(event.player, 3, 3*min(i,16))
        }
        */

        for (LevelManager.ExpCat expcat : LevelManager.ExpCat.values())
            manager.addExp(player, expcat, pluginInstance.getConfig().getInt("Broken." + expcat.getName() + "." + block.name()));
    }
}
