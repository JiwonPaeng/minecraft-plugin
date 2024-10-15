package com.paeng.testPlugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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

    public static HashMap<String, Integer> combatLevel = new HashMap<>();
    public static HashMap<String, Integer> miningLevel = new HashMap<>();
    public static HashMap<String, Integer> forageLevel = new HashMap<>();
    public static HashMap<String, Integer> farmingLevel = new HashMap<>();
    public static HashMap<String, Integer> fishingLevel = new HashMap<>();

    public static YamlConfiguration modifiedFile;
    public static File file;

    public enum ExpCat {
        COMBAT(0), MINING(1), FORAGE(2), FARMING(3), FISHING(4);
        private final int code;
        private final String[] names = {"combat", "mining", "forage", "farming", "fishing"};
        private final String[] styledNamesOpening = {"§c[\uD83D\uDDE1", "§e[⛏", "§6[\uD83E\uDE93", "§a[☘", "§3[\uD83C\uDFA3", "§d[⦾"};
        private final String[] coloredBars = {"§c|", "§e|", "§6|", "§a|", "§3|", "§d|"};
        ExpCat(final int desCode) { code = desCode; }
        public int getCode() { return code; }
        public String getName() { return names[code]; }
        public String getStyledNameOpening() { return styledNamesOpening[code]; }
        public String getColoredBar() { return coloredBars[code]; }
    }

    private HashMap<String, Integer> getExpData(ExpCat category) {
        if (category.getCode() == 0) return combatExp;
        if (category.getCode() == 1) return miningExp;
        if (category.getCode() == 2) return forageExp;
        if (category.getCode() == 3) return farmingExp;
        if (category.getCode() == 4) return fishingExp;
        return null;
    }

    private HashMap<String, Integer> getLevelData(ExpCat category) {
        if (category.getCode() == 0) return combatLevel;
        if (category.getCode() == 1) return miningLevel;
        if (category.getCode() == 2) return forageLevel;
        if (category.getCode() == 3) return farmingLevel;
        if (category.getCode() == 4) return fishingLevel;
        return null;
    }

    public void addExp(Player player, ExpCat category, int value) {
        if (value == 0) return;
        String UUIDString = player.getUniqueId().toString();

        getExpData(category).put(UUIDString, getExpData(category).get(UUIDString) + value);

        while (getExpData(category).get(UUIDString) >= getExpRequirements(getLevel(player, category))) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1.0F,1.0F);
            String[] icon = {"§c\uD83D\uDDE1 전투", "§e⛏ 채광", "§6\uD83E\uDE93 벌목", "§a☘ 농사", "§3\uD83C\uDFA3 낚시"};

            getExpData(category).put(UUIDString, getExpData(category).get(UUIDString) - getExpRequirements(getLevel(player, category)));
            getLevelData(category).put(UUIDString, getLevelData(category).get(UUIDString) + 1);

            Bukkit.broadcastMessage("§e↑ " + player.getName()+"§f님이 "+ icon[category.getCode()] + " §e" + getLevel(player, category) +"레벨§f을 달성했습니다!");
        }

        double progress = getProgress(player, category);
        String message = String.format(category.getStyledNameOpening() + getLevel(player, category) + "] §b(+" + value + ") "
                        + category.getColoredBar().repeat(((int) progress) / 2) + "§7|".repeat(50 - ((int) progress) / 2)
                        + " §7(%.1f%%)", progress);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }


    public int getTotalLevel(Player player) {
        int total_level = 0;
        for (ExpCat category : ExpCat.values()) {
            total_level += getLevel(player, category);
        }
        return total_level;
    }

    int[] exp_requirements = {10000, 10000, 20000, 20000, 30000, 30000, 40000, 40000, 50000, 50000, 50000};
    private int getExpRequirements(int i) {
        if (i < 10) return exp_requirements[i];
        return exp_requirements[10];
    }

    public int getLevel(Player player, ExpCat category) {
        return getLevelData(category).get(player.getUniqueId().toString());
    }

    public double getProgress(Player player, ExpCat category) {
        int experience_points = getExpData(category).get(player.getUniqueId().toString());
        int level = getLevel(player, category);
        return (double) experience_points / getExpRequirements(level) * 100;
    }

    // save HashMap Data to config.yml
    public void saveLevelData() {
        for (ExpCat category : ExpCat.values()) {
            for (Map.Entry<String, Integer> element : getExpData(category).entrySet())
                modifiedFile.set("Data." + element.getKey() + "." + category.getName() + ".exp", element.getValue());
            for (Map.Entry<String, Integer> element : getLevelData(category).entrySet())
                modifiedFile.set("Data." + element.getKey() + "." + category.getName() + ".level", element.getValue());
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
            for (ExpCat expcat : ExpCat.values()) {
                getExpData(expcat).put(UUID, modifiedFile.getInt("Data." + UUID + "." + expcat.getName() + ".exp"));
                getLevelData(expcat).put(UUID, modifiedFile.getInt("Data." + UUID + "." + expcat.getName() + ".level"));
            }
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

        for (ExpCat expcat : ExpCat.values()) {
            getExpData(expcat).put(UUID, 0);
            getLevelData(expcat).put(UUID, 0);
        }
    }
}
