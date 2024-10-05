package com.paeng.testPlugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEventListener implements Listener {

    public String getLevelStyle(int level) {

        if (level < 30) return ChatColor.of("#7F7F7F") + "[" + level + "★]";
        if (level < 50) return ChatColor.of("#E77B57") + "[" + level + "★]";
        if (level < 60) return ChatColor.of("#D1D1D1") + "[" + level + "★]";
        if (level < 70) return ChatColor.of("#F8D547") + "[" + level + "★]";
        if (level < 80) return ChatColor.of("#63E989") + "[" + level + "★]";
        if (level < 90) return ChatColor.of("#71E0DA") + "[" + level + "★]";
        if (level < 100) return ChatColor.of("#F24241") + "[" + level + "★]";

        if (level < 120) return ChatColor.of("#CFB76E") + "[" + ChatColor.of("#E2E2E2") + level + "★" + ChatColor.of("#CFB76E") + "]"; //WhiteGold
        if (level < 140) return ChatColor.of("#DBC8B2") + "[" + ChatColor.of("#AE8A66") + level + "❋" + ChatColor.of("#DBC8B2") + "]"; //CaffeLatte
        if (level < 160) return ChatColor.of("#4D484D") + "[" + ChatColor.of("#59575A") + level + "❋" + ChatColor.of("#4D484D") + "]"; //Netherite
        if (level < 180) return ChatColor.of("#1C7E6D") + "[" + ChatColor.of("#30CAAE") + level + "★" + ChatColor.of("#1C7E6D") + "]"; //Pearl
        if (level < 200) return ChatColor.of("#EB4540") + "[" + ChatColor.of("#FEF7E1") + level + "❋" + ChatColor.of("#EB4540") + "]"; //StrawberryCake
        if (level < 220) return ChatColor.of("#86DDFF") + "[" + ChatColor.of("#2DA1FE") + level + ChatColor.of("#4F78FE") + "☽" + ChatColor.of("#374EEE") + "]"; //Zenith
        if (level < 240) return ChatColor.of("#FFE737") + "[" + ChatColor.of("#FFF07F") + level + ChatColor.of("#ADFCFF") + "✵" + ChatColor.of("#FFF5AD") + "]"; //Luxury
        if (level < 260) return ChatColor.of("#E4FFC5") + "[" + ChatColor.of("#FFD5C5") + level + ChatColor.of("#FDFFC5") + "✵" + ChatColor.of("#E4FFC5") + "]"; //Nature
        if (level < 280) return ChatColor.of("#2E2D00") + "[" + ChatColor.of("#FFFFFF") + level + ChatColor.of("#FFFB00") + "☽" + ChatColor.of("#2E2D00") + "]"; //Moonlight
        return ChatColor.of("#A1FFFB") + "§k|" + ChatColor.of("#C9FFFD") + level + ChatColor.of("#E5FFFE") + "★" + ChatColor.of("#FFFFFF") + "§k|"; //WhiteNoise

        /*

        // Rainbow
        val stringBuilder = StringBuilder()
        val rainbowList = "§c§6§e§a§b§9§c§6§e§a§b§9".chunked(2)
        val everySplit = "[$level✵]".chunked(1)
        for (i in everySplit.indices) {
            stringBuilder.append(rainbowList[i])
            stringBuilder.append(everySplit[i])
        }
        stringBuilder.toString()


        // Modern
        //１２３４５６７８９０
        val str = NumberConvert.toRoman(level)
            "" + ChatColor.of("#98B2BF") + "[" + ChatColor.of("#F1EFD6") + str + "⚝" + ChatColor.of("#98B2BF") + "]"


        // Christmas
        val stringBuilder = StringBuilder()
        val rainbowList = "§c§a§c§a§c§a§c§a§c§a".chunked(2)
        val everySplit = "[$level◎]".chunked(1)
        for (i in everySplit.indices) {
            stringBuilder.append(rainbowList[i])
            stringBuilder.append(everySplit[i])
        }
        stringBuilder.toString()


        // NoteBlock
        val str = level.toString().replace("1", "§c♪")
                .replace("6", "§b♪")
                .replace("9", "§d♪")
                .replace("2", "§6♪")
                .replace("3", "§e♪")
                .replace("4", "§a♪")
                .replace("5", "§2♪")
                .replace("7", "§9♪")
                .replace("8", "§5♪")
                .replace("0", "§4♪")
        "" + ChatColor.of("#725D4B") + "[" + str + "§f♪" + ChatColor.of("#725D4B") + "]"


        //RModern
        val str = NumberConvert.toRoman(level)
        val stringBuilder = StringBuilder()
        val rainbowList = "§c§6§e§a§b§9§c§6§e§a§b§9".chunked(2)
        val everySplit = "[$str⚝]".chunked(1)
        for (i in everySplit.indices) {
            stringBuilder.append(rainbowList[i])
            stringBuilder.append(everySplit[i])
        }
        stringBuilder.toString()

        */
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        LevelManager manager = new LevelManager();
        String levelDisplay = getLevelStyle(manager.getLevel(player));

        event.setFormat(levelDisplay + "§f %1$s: %2$s");
    }
}