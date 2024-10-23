package com.paeng.testPlugin;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.coreprotect.CoreProtect;

import java.util.ArrayList;

public class BlockBreakEventListener implements Listener {

    private ArrayList<Location> recentBlocks = new ArrayList<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        recentBlocks.add(event.getBlock().getLocation());
        if (recentBlocks.size() > 100) recentBlocks.removeFirst();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        CoreProtectAPI coreProtect = getCoreProtect();

        Material block = event.getBlock().getType();
        Player player = event.getPlayer();

        LevelManager manager = new LevelManager();
        TestPlugin pluginInstance = JavaPlugin.getPlugin(TestPlugin.class);

        if (block == Material.PUMPKIN || block == Material.MELON) {
            if (coreProtect.blockLookup(event.getBlock(), 120).isEmpty() && !recentBlocks.contains(event.getBlock().getLocation()))
                manager.addExp(player, LevelManager.ExpCat.FARMING, pluginInstance.getConfig().getInt("Broken.farming." + block.name()));
            return;
        }

        if (block == Material.SUGAR_CANE || block == Material.BAMBOO) {
            if (!coreProtect.blockLookup(event.getBlock(), 120).isEmpty() || recentBlocks.contains(event.getBlock().getLocation())) return;
            Location event_location = event.getBlock().getLocation();
            int height = event.getBlock().getLocation().getBlockY();
            for (; height < 320; height++) {
                Location location = new Location(event.getBlock().getWorld(), event_location.getBlockX(), height, event_location.getBlockZ());
                if (location.getBlock().getType() != block || !coreProtect.blockLookup(location.getBlock(), 150).isEmpty()) break;
            }
            manager.addExp(player, LevelManager.ExpCat.FARMING, (height - event.getBlock().getLocation().getBlockY()) * pluginInstance.getConfig().getInt("Broken.farming." + block.name()));
            return;
        }

        if (block == Material.WHEAT || block == Material.CARROTS || block == Material.POTATOES || block == Material.NETHER_WART) {
            if (!coreProtect.blockLookup(event.getBlock(), 120).isEmpty() || recentBlocks.contains(event.getBlock().getLocation())) return;
            Ageable ageable = (Ageable) event.getBlock().getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge()) manager.addExp(player, LevelManager.ExpCat.FARMING, pluginInstance.getConfig().getInt("Broken.farming." + block.name()));
            return;
        }

        if (!coreProtect.blockLookup(event.getBlock(), 2147000000).isEmpty()) return;

        if (recentBlocks.contains(event.getBlock().getLocation())) return;

        for (LevelManager.ExpCat expcat : LevelManager.ExpCat.values())
            manager.addExp(player, expcat, pluginInstance.getConfig().getInt("Broken." + expcat.getName() + "." + block.name()));
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

        CoreProtect cp = (CoreProtect) plugin;

        return cp.getAPI();
    }
}
