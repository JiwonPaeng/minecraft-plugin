package com.paeng.testPlugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEventListener implements Listener {

    public String getLevelStyle(int level) {

        String levelString = level + "";

        /* 1-99 Gem Prestige */
        if (level < 10) return ChatColor.of("#7F7F7F") + "[" + level + "★]"; //Stone
        if (level < 20) return ChatColor.of("#8A3907") + "[" + level + "★]"; //Copper
        if (level < 30) return ChatColor.of("#E77B57") + "[" + level + "★]"; //Bronze
        if (level < 40) return ChatColor.of("#D1D1D1") + "[" + level + "★]"; //Silver
        if (level < 50) return ChatColor.of("#F8D547") + "[" + level + "★]"; //Gold
        if (level < 60) return ChatColor.of("#63E989") + "[" + level + "★]"; //Platinum
        if (level < 70) return ChatColor.of("#71E0DA") + "[" + level + "★]"; //Diamond
        if (level < 80) return ChatColor.of("#F24241") + "[" + level + "★]"; //Ruby
        if (level < 90) return ChatColor.of("#A137ED") + "[" + level + "★]"; //Amethyst
        if (level < 100) return ChatColor.of("#D899DE") + "[" + level + "★]"; //Crystal

        /* 100-299 Nature Prestige */
        if (level < 120) return ChatColor.of("#4D484D") + "[" + ChatColor.of("#C0C0C0") + level + "❋" + ChatColor.of("#4D484D") + "]"; //BasaltDeltas
        if (level < 140) return ChatColor.of("#B76E79") + "[" + ChatColor.of("#E0BFB8") + level + "❋" + ChatColor.of("#B76E79") + "]"; //CrimsonForest
        if (level < 160) return ChatColor.of("#1C7E6D") + "[" + ChatColor.of("#30CAAE") + level + "❋" + ChatColor.of("#1C7E6D") + "]"; //TheEnd
        if (level < 180) return ChatColor.of("#DBC8B2") + "[" + ChatColor.of("#AE8A66") + level + "❋" + ChatColor.of("#DBC8B2") + "]"; //DripstoneCave
        if (level < 200) return ChatColor.of("#1E90FF") + "[" + ChatColor.of("#4169E1") + level + "❋" + ChatColor.of("#1E90FF") + "]"; //Ocean
        if (level < 220) return ChatColor.of("#CFB76E") + "[" + ChatColor.of("#E2E2E2") + level + "❋" + ChatColor.of("#CFB76E") + "]"; //SnowyBeach
        if (level < 240) return ChatColor.of("#F4A460") + "[" + ChatColor.of("#F5DEB3") + level + "❋" + ChatColor.of("#F4A460") + "]"; //Desert
        if (level < 260) return ChatColor.of("#3CB371") + "[" + ChatColor.of("#90EE90") + level + "❋" + ChatColor.of("#3CB371") + "]"; //Forest
        if (level < 280) return ChatColor.of("#BA55D3") + "[" + ChatColor.of("#EE82EE") + level + "❋" + ChatColor.of("#BA55D3") + "]"; //CherryGrove
        if (level < 300) return ChatColor.of("#E0FFFF") + "[" + ChatColor.of("#F0F8FF") + level + "❋" + ChatColor.of("#E0FFFF") + "]"; //FrozenPeaks


        /* 300-499 Sky Prestige */
        if (level < 320) return ChatColor.of("#FFA07A") + "[" + ChatColor.of("#DB7093") + level + ChatColor.of("#E6E6FA") + "⚝" + ChatColor.of("#7B68EE") + "]"; //Dusk
        if (level < 340) return ChatColor.of("#FFA07A") + "[" + ChatColor.of("#FF6347") + level + ChatColor.of("#FFA500") + "⚝" + ChatColor.of("#FFD700") + "]"; //Sunrise
        if (level < 360) return ChatColor.of("#4682B4") + "[" + ChatColor.of("#5F9EA0") + level + ChatColor.of("#4169E1") + "⚝" + ChatColor.of("#AFEEEE") + "]"; //RainyDay
        if (level < 380) return ChatColor.of("#E4FFC5") + "[" + ChatColor.of("#FFD5C5") + level + ChatColor.of("#FDFFC5") + "⚝" + ChatColor.of("#E4FFC5") + "]"; //Rainbow
        if (level < 400) return ChatColor.of("#FFE737") + "[" + ChatColor.of("#FFF07F") + level + ChatColor.of("#ADFCFF") + "⚝" + ChatColor.of("#FFF5AD") + "]"; //GoldenHour
        if (level < 420) return ChatColor.of("#6495ED") + "[" + ChatColor.of("#87CEFA") + level + ChatColor.of("#FFDAB9") + "⚝" + ChatColor.of("#F08080") + "]"; //Sunset
        if (level < 440) return ChatColor.of("#6A5ACD") + "[" + ChatColor.of("#483D8B") + level + ChatColor.of("#4B0082") + "⚝" + ChatColor.of("#800080") + "]"; //Dawn
        if (level < 460) return ChatColor.of("#FFD700") + "[" + ChatColor.of("#FFFF00") + level + ChatColor.of("#FFDAB9") + "⚝" + ChatColor.of("#FFFACD") + "]"; //NightCity
        if (level < 480) return ChatColor.of("#778899") + "[" + ChatColor.of("#D96C8B") + levelString.charAt(0) + ChatColor.of("#F0E68C") + levelString.charAt(1) + ChatColor.of("#98FB98") + levelString.charAt(2) + ChatColor.of("#00BFFF") + "⚝" + ChatColor.of("#778899") + "]"; //Firework
        if (level < 500) return ChatColor.of("#86DDFF") + "[" + ChatColor.of("#2DAEFE") + levelString.charAt(0) + ChatColor.of("#2DA1FE") + levelString.charAt(1) + ChatColor.of("#2D92FE") + levelString.charAt(2) + ChatColor.of("#4F78FE") + "⚝" + ChatColor.of("#374EEE") + "]"; //Midnight


        /* 500+ Paeng Prestige */
        return ChatColor.of("#87CEEB") + "[" + ChatColor.of("#00BFFF") + "The Paeng" + ChatColor.of("#2DA1FE") + "✵" + ChatColor.of("#87CEEB") + "]"; //The Paeng

    }

    public String __getLevelStyle(int level) {

        String levelString = level + "";

        /* 1-99 Gem Prestige */
        if (level < 10) return ChatColor.of("#7F7F7F") + "[" + level + "★]"; //Stone
        if (level < 20) return ChatColor.of("#8A3907") + "[" + level + "★]"; //Copper
        if (level < 30) return ChatColor.of("#E77B57") + "[" + level + "★]"; //Bronze
        if (level < 40) return ChatColor.of("#D1D1D1") + "[" + level + "★]"; //Silver
        if (level < 50) return ChatColor.of("#F8D547") + "[" + level + "★]"; //Gold
        if (level < 60) return ChatColor.of("#63E989") + "[" + level + "★]"; //Platinum
        if (level < 70) return ChatColor.of("#71E0DA") + "[" + level + "★]"; //Diamond
        if (level < 80) return ChatColor.of("#F24241") + "[" + level + "★]"; //Ruby
        if (level < 90) return ChatColor.of("#A137ED") + "[" + level + "★]"; //Amethyst
        if (level < 100) return ChatColor.of("#D899DE") + "[" + level + "★]"; //Crystal

        /* 100-199 Nature Prestige */

        if (level < 110) return ChatColor.of("#4D484D") + "[" + ChatColor.of("#59575A") + level + "❋" + ChatColor.of("#4D484D") + "]"; //BasaltDeltas
        if (level < 120) return ChatColor.of("#B76E79") + "[" + ChatColor.of("#E0BFB8") + level + "❋" + ChatColor.of("#B76E79") + "]"; //CrimsonForest
        if (level < 130) return ChatColor.of("#1C7E6D") + "[" + ChatColor.of("#30CAAE") + level + "❋" + ChatColor.of("#1C7E6D") + "]"; //TheEnd
        if (level < 140) return ChatColor.of("#DBC8B2") + "[" + ChatColor.of("#AE8A66") + level + "❋" + ChatColor.of("#DBC8B2") + "]"; //DripstoneCave
        if (level < 150) return ChatColor.of("#000080") + "[" + ChatColor.of("#4169E1") + level + "❋" + ChatColor.of("#000080") + "]"; //Ocean
        if (level < 160) return ChatColor.of("#CFB76E") + "[" + ChatColor.of("#E2E2E2") + level + "❋" + ChatColor.of("#CFB76E") + "]"; //SnowyBeach
        if (level < 170) return ChatColor.of("#F4A460") + "[" + ChatColor.of("#F5DEB3") + level + "❋" + ChatColor.of("#F4A460") + "]"; //Desert
        if (level < 180) return ChatColor.of("#3CB371") + "[" + ChatColor.of("#90EE90") + level + "❋" + ChatColor.of("#3CB371") + "]"; //Forest
        if (level < 190) return ChatColor.of("#BA55D3") + "[" + ChatColor.of("#EE82EE") + level + "❋" + ChatColor.of("#BA55D3") + "]"; //CherryGrove
        if (level < 200) return ChatColor.of("#E0FFFF") + "[" + ChatColor.of("#F0F8FF") + level + "❋" + ChatColor.of("#E0FFFF") + "]"; //FrozenPeaks


        /* 200-299 Sky Prestige */
        if (level < 210) return ChatColor.of("#FFA07A") + "[" + ChatColor.of("#DB7093") + level + ChatColor.of("#E6E6FA") + "⚝" + ChatColor.of("#7B68EE") + "]"; //Dusk
        if (level < 220) return ChatColor.of("#FFA07A") + "[" + ChatColor.of("#FF6347") + level + ChatColor.of("#FFA500") + "⚝" + ChatColor.of("#FFD700") + "]"; //Sunrise
        if (level < 230) return ChatColor.of("#4682B4") + "[" + ChatColor.of("#5F9EA0") + level + ChatColor.of("#4169E1") + "⚝" + ChatColor.of("#AFEEEE") + "]"; //RainyDay
        if (level < 240) return ChatColor.of("#E4FFC5") + "[" + ChatColor.of("#FFD5C5") + level + ChatColor.of("#FDFFC5") + "⚝" + ChatColor.of("#E4FFC5") + "]"; //Rainbow
        if (level < 250) return ChatColor.of("#FFE737") + "[" + ChatColor.of("#FFF07F") + level + ChatColor.of("#ADFCFF") + "⚝" + ChatColor.of("#FFF5AD") + "]"; //GoldenHour
        if (level < 260) return ChatColor.of("#6495ED") + "[" + ChatColor.of("#87CEFA") + level + ChatColor.of("#FFDAB9") + "⚝" + ChatColor.of("#F08080") + "]"; //Sunset
        if (level < 270) return ChatColor.of("#6A5ACD") + "[" + ChatColor.of("#483D8B") + level + ChatColor.of("#4B0082") + "⚝" + ChatColor.of("#800080") + "]"; //Dawn
        if (level < 280) return ChatColor.of("#FFD700") + "[" + ChatColor.of("#FFFF00") + level + ChatColor.of("#FFDAB9") + "⚝" + ChatColor.of("#FFFACD") + "]"; //NightCity
        if (level < 290) return ChatColor.of("#778899") + "[" + ChatColor.of("#D96C8B") + levelString.charAt(0) + ChatColor.of("#F0E68C") + levelString.charAt(1) + ChatColor.of("#98FB98") + levelString.charAt(2) + ChatColor.of("#00BFFF") + "⚝" + ChatColor.of("#778899") + "]"; //Firework
        if (level < 300) return ChatColor.of("#86DDFF") + "[" + ChatColor.of("#2DA1FE") + level + ChatColor.of("#4F78FE") + "⚝" + ChatColor.of("#374EEE") + "]"; //Midnight


        /* 300+ The Paeng */
        return ChatColor.of("#00BFFF") + "[" + ChatColor.of("#00BFFF") + "The Paeng" + ChatColor.of("#00BFFF") + "✵" + ChatColor.of("#00BFFF") + "]"; //The Paeng

    }

    public String _getLevelStyle(int level) {

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

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        LevelManager manager = new LevelManager();
        String levelDisplay = getLevelStyle(manager.getTotalLevel(player.getUniqueId()));

        event.setFormat(levelDisplay + "§f %1$s: %2$s");
    }
}