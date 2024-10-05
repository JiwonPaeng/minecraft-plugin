package com.paeng.testPlugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LevelManager {

    public static HashMap<String, Integer> combatExp = new HashMap<>();
    public static HashMap<String, Integer> miningExp = new HashMap<>();
    public static HashMap<String, Integer> forageExp = new HashMap<>();
    public static HashMap<String, Integer> farmingExp = new HashMap<>();
    public static HashMap<String, Integer> fishingExp = new HashMap<>();
    public static HashMap<String, Integer> buildingExp = new HashMap<>();

    public static YamlConfiguration modifiedFile;
    public static File file;

    public enum ExpCat {
        COMBAT(0), MINING(1), FORAGE(2), FARMING(3), FISHING(4), BUILDING(5);
        private final int code;
        private final String[] names = {"combat", "mining", "forage", "farming", "fishing", "building"};
        private final String[] styledNames = {"§c[\uD83D\uDDE1]", "§e[⛏]", "§6[\uD83E\uDE93]", "§a[☘]", "§3[\uD83C\uDFA3]", "§d[⦾]"};
        private final String[] coloredBars = {"§c|", "§e|", "§6|", "§a|", "§3|", "§d|"};
        ExpCat(final int desCode) { code = desCode; }
        public int getCode() { return code; }
        public String getName() { return names[code]; }
        public String getStyledName() { return styledNames[code]; }
        public String getColoredBar() { return coloredBars[code]; }
    }

    private HashMap<String, Integer> getLevelData(ExpCat category) {
        if (category.getCode() == 0) return combatExp;
        if (category.getCode() == 1) return miningExp;
        if (category.getCode() == 2) return forageExp;
        if (category.getCode() == 3) return farmingExp;
        if (category.getCode() == 4) return fishingExp;
        if (category.getCode() == 5) return buildingExp;
        return null;
    }

    public void addExp(String UUID, ExpCat category, int value) {
        if (value == 0) return;

        Player player = Bukkit.getPlayer(java.util.UUID.fromString(UUID));
        int permil = (getLevelData(category).get(UUID) % 10000) / 10;

        Objects.requireNonNull(getLevelData(category)).put(UUID, Objects.requireNonNull(getLevelData(category)).get(UUID) + value);

        String message = category.getStyledName() + " §b(+" + value + ") "
                + category.getColoredBar().repeat(permil / 20) + "§7|".repeat(50 - permil / 20)
                + " §7(" + permil / 10 + "." + permil % 10 + "%)";

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    // save HashMap Data to config.yml
    public void saveLevelData() {
        for (ExpCat expcat : ExpCat.values()) {
            for (Map.Entry<String, Integer> element : Objects.requireNonNull(getLevelData(expcat)).entrySet())
                modifiedFile.set("Data." + element.getKey() + "." + expcat.getName(), element.getValue());
        }

        // pluginInstance.saveConfig();
        try {
            modifiedFile.save(file);
        } catch (IOException exception) {
            Bukkit.getLogger().warning("IO Exception while trying to save file");
        }
    }

    // load config.yml Data to HashMap
    public void loadLevelData() {
        ConfigurationSection dataSection = modifiedFile.getConfigurationSection("Data");

        if (dataSection == null) return;

        for (String UUID : dataSection.getKeys(false)) {
            for (ExpCat expcat : ExpCat.values())
                Objects.requireNonNull(getLevelData(expcat)).put(UUID, modifiedFile.getInt("Data." + UUID + "." + expcat.getName()));
        }
    }

    // When Server loads
    public void startUp() {
        TestPlugin pluginInstance = JavaPlugin.getPlugin(TestPlugin.class);

        // save default config.yml
        pluginInstance.getConfig().options().copyDefaults(true);
        pluginInstance.saveConfig();

        // data.yml logic
        file = new File(pluginInstance.getDataFolder(), "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                Bukkit.getLogger().warning("IO Exception while trying to create data.yml");
                return;
            }
        }
        modifiedFile = YamlConfiguration.loadConfiguration(file);
    }

    // new player joining logic
    public void addNewPlayerData(String UUID) {
        for (Map.Entry<String, Integer> element : combatExp.entrySet()) {
            if (UUID.equals(element.getKey())) return;
        }

        for (ExpCat expcat : ExpCat.values()) Objects.requireNonNull(getLevelData(expcat)).put(UUID, 0);
    }
}
