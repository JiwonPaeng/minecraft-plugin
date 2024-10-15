package com.paeng.testPlugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerFishEventListener implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        if (!(event.getCaught() instanceof Item)) return;

        LevelManager manager = new LevelManager();
        TestPlugin pluginInstance = JavaPlugin.getPlugin(TestPlugin.class);

        Player player = event.getPlayer();
        World cur_world = player.getWorld();
        Location hook = event.getHook().getLocation();
        Item stack = (Item) event.getCaught();

        int waterCount = 0;
        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                for (int z = -2; z < 3; z++) {
                    if (cur_world.getBlockAt(hook.getBlockX() + x, hook.getBlockY() + y, hook.getBlockZ() + z).getType() != Material.WATER) continue;
                    waterCount++;
                }
            }
        }

        if (waterCount < 50) {
            int weight = (int) (Math.random() * 4);
            Material[] materials = {Material.STICK, Material.BOWL, Material.ROTTEN_FLESH, Material.GLASS_BOTTLE};
            String[] material_name = {"막대기", "그릇", "썩은 살점", "유리병"};

            stack.setItemStack(new ItemStack(materials[weight]));
            String caught = material_name[weight];

            player.sendMessage("§7§lCOMMON §f" + caught + " 을(를) 낚았다.");
            player.sendMessage("§c[!] 낚시 위치가 좋지 않습니다. 물이 많은 곳으로 이동하세요 §7(위치 점수: " + waterCount * 2 + "/100)");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS,1.0F,0.5F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§a[!] 위치 점수가 100점일 때만 낚시가 가능합니다"));
            return;
        }

        int weight_class = (int) (Math.random() * 1000);
        int weight_item = (int) (Math.random() * 100);

        int item_class_code;
        String[] item_class_names = {"FISH", "COMMON", "RARE", "EPIC", "LEGENDARY"};
        if (weight_class < 800) item_class_code = 0; // FISH : 80.0%
        else if (weight_class < 900) item_class_code = 1; // COMMON : 10.0% -> 10%
        else if (weight_class < 995) item_class_code = 2; // RARE : 9.5% -> 9.5%
        else if (weight_class < 999) item_class_code = 3; // EPIC : 0.45% -> 0.4%
        else item_class_code = 4; // LEGENDARY : 0.05% -> 0.1%
        String item_class = item_class_names[item_class_code];

        ConfigurationSection configurationSection = pluginInstance.getConfig().getConfigurationSection("Caught." + item_class);

        List<Integer> item_weights = new ArrayList<>();

        for (String materialName : configurationSection.getKeys(false))
            item_weights.add(pluginInstance.getConfig().getInt("Caught." + item_class + "." + materialName + ".weight"));

        Collections.sort(item_weights);

        int weight_of_desired_item = 100;
        for (Integer itemWeight : item_weights) {
            if (weight_item >= itemWeight) continue;
            weight_of_desired_item = itemWeight;
            break;
        }

        String caught_material_name = "";
        for (String materialName : configurationSection.getKeys(false)) {
            if (pluginInstance.getConfig().getInt("Caught." + item_class + "." + materialName + ".weight") != weight_of_desired_item) continue;
            caught_material_name = materialName;
        }

        // 1. Play Sound
        float[] pitch_data = {0.5F, 0.5F, 2.0F, 0.5F, 1.0F};
        Sound[] sound_data = {Sound.ITEM_BUCKET_FILL_FISH, Sound.BLOCK_NOTE_BLOCK_BASS, Sound.BLOCK_NOTE_BLOCK_BELL, Sound.ENTITY_PLAYER_LEVELUP, Sound.UI_TOAST_CHALLENGE_COMPLETE};
        player.playSound(player.getLocation(), sound_data[item_class_code], 1.0F, pitch_data[item_class_code]);

        // 2. Broadcast Information
        String[] item_class_styles = {"§3", "§7", "§b", "§d", "§e"};
        String name_kor = pluginInstance.getConfig().getString("Caught." + item_class + "." + caught_material_name + ".name");
        if (item_class_code < 3) player.sendMessage(item_class_styles[item_class_code] + item_class + " §f" + name_kor + " 을(를) 낚았다.");
        else Bukkit.broadcastMessage(item_class_styles[item_class_code] + item_class + "! §f" + player.getName() + "님이 " + name_kor + " §f을(를) 낚았습니다!");

        // 3. Grant Experience Points
        manager.addExp(player, LevelManager.ExpCat.FISHING, pluginInstance.getConfig().getInt("Caught." + item_class + "." + caught_material_name + ".reward"));

        // In-Game EXP Edge Case
        if (item_class.equals("EPIC") && name_kor.equals("1500 인첸트 경험치")) event.setExpToDrop(1500);

        // 4. Change ItemStack
        if (item_class.equals("RARE") && name_kor.equals("마법이 부여된 낚싯대")) stack.setItemStack(randomEnchantment(new ItemStack(Material.FISHING_ROD)));
        else if (item_class.equals("RARE") && name_kor.equals("마법이 부여된 활")) stack.setItemStack(randomEnchantment(new ItemStack(Material.BOW)));
        else if (item_class.equals("LEGENDARY") && name_kor.equals("마법이 부여된 네더라이트 곡괭이")) stack.setItemStack(randomEnchantment(new ItemStack(Material.NETHERITE_PICKAXE)));
        else if (item_class.equals("LEGENDARY") && name_kor.equals("마법이 부여된 네더라이트 투구")) stack.setItemStack(randomEnchantment(new ItemStack(Material.NETHERITE_HELMET)));
        else if (item_class.equals("LEGENDARY") && name_kor.equals("무한 수선 활")) stack.setItemStack(infMendEnchantment(new ItemStack(Material.BOW)));
        else stack.setItemStack(new ItemStack(Material.valueOf(caught_material_name)));
    }

    public ItemStack randomEnchantment(ItemStack item) {
        List<Enchantment> candidates = new ArrayList<>();

        for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if (enchantment.canEnchantItem(item))
                candidates.add(enchantment);
        }

        candidates.remove(Enchantment.MENDING);

        if (candidates.isEmpty()) return item;

        Collections.shuffle(candidates);
        int max = Math.min(candidates.size(),(int) Math.ceil(Math.random() * 4));

        for (int i = 0; i < max; i++) {
            Enchantment chosen = candidates.get(i);
            item.addEnchantment(chosen, 1 + (int) (Math.random() * ((chosen.getMaxLevel() - 1) + 1)));
        }

        return item;
    }

    public ItemStack infMendEnchantment(ItemStack item) {
        item.addUnsafeEnchantment(Enchantment.POWER, (int) Math.ceil(Math.random()*5));
        item.addUnsafeEnchantment(Enchantment.FLAME, 1);
        item.addUnsafeEnchantment(Enchantment.INFINITY, 1);
        item.addUnsafeEnchantment(Enchantment.MENDING, 1);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, (int) Math.ceil(Math.random()*2));
        return item;
    }
}