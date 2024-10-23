package com.paeng.testPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class Profile implements CommandExecutor, Listener {

    private final String[] catDes_1 = {"§7몬스터를 사냥하여", "§7돌과 광물을 채굴하여", "§7나무를 캐서", "§7농작물을 수확하여", "§7물고기를 잡거나 보물을 낚아"};
    private final Material[] represents = {Material.NETHERITE_SWORD, Material.IRON_PICKAXE, Material.GOLDEN_AXE, Material.STONE_HOE, Material.FISHING_ROD};

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equals("§0프로필"))) return;
        event.setCancelled(true);
        final ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        final Player player = (Player) event.getWhoClicked();
        final int slot = event.getRawSlot();

        if (slot == 8) player.closeInventory();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        LevelManager manager = new LevelManager();
        OfflinePlayer target = (OfflinePlayer) player;
        if (args.length >= 1) {
            for (String uuid : manager.getLevelData(LevelManager.ExpCat.COMBAT).keySet()) {
                if (!Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName().equalsIgnoreCase(args[0])) continue;
                target = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            }
        }

        Inventory inv = Bukkit.createInventory(null, 45, "§0프로필");
        for (int i = 0; i < 45; i++) inv.setItem(i, createItem(Material.BLACK_STAINED_GLASS_PANE," "));

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();

        if (args.length == 0) {
            List<String> lore = new ArrayList<>();
            lore.add("§e★§f 레벨: §e" + manager.getTotalLevel(player.getUniqueId()));
            int ticks_played = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
            lore.add("§b⌚§f 접속 시간: §b" + ticks_played / 72000 + "시간 " + (ticks_played % 72000) / 1200 + "분");
            Objects.requireNonNull(meta).setOwningPlayer(player);
            meta.setLore(lore);
        }
        else {
            meta.setOwningPlayer(target);
        }

        meta.setDisplayName("§6⬛ " + target.getName() + "님의 정보");
        playerHead.setItemMeta(meta);
        inv.setItem(4,playerHead);

        inv.setItem(8,createItem(Material.BARRIER,"§c✖ 닫기"));

        for (LevelManager.ExpCat category : LevelManager.ExpCat.values()) {
            List<String> users = new ArrayList<>();
            for (Map.Entry<String, Integer> element : manager.getExpData(category).entrySet()) users.add(element.getKey());

            for (int i = 0; i < users.size() - 1; i++) {
                for (int j = i + 1; j < users.size(); j++) {
                    int level_left = manager.getLevelData(category).get(users.get(i));
                    int level_right = manager.getLevelData(category).get(users.get(j));
                    if (level_left > level_right) continue;
                    if (level_left < level_right) {
                        String temp = users.get(i);
                        users.set(i, users.get(j));
                        users.set(j, temp);
                        continue;
                    }
                    int exp_left = manager.getExpData(category).get(users.get(i));
                    int exp_right = manager.getExpData(category).get(users.get(j));
                    if (exp_left > exp_right) continue;
                    String temp = users.get(i);
                    users.set(i, users.get(j));
                    users.set(j, temp);
                }
            }
            String[] leaders = {"", "", ""};
            for (int i = 0; i < 3; i++) {
                if (i >= users.size()) continue;
                UUID uuid = UUID.fromString(users.get(i));
                OfflinePlayer leader = Bukkit.getOfflinePlayer(uuid);
                leaders[i] = " "+ leader.getName() + " §7Lv" + manager.getLevel(uuid, category) + " §8" + String.format("%.2f", manager.getProgress(uuid, category))+"%";
            }

            int repeatCnt = (int) Math.round(manager.getProgress(target.getUniqueId(), category) / 5);

            inv.setItem(20 + category.getCode(), createItem(represents[category.getCode()], category.getIcon() + " §7Lv" + manager.getLevel(target.getUniqueId(), category),
                    catDes_1[category.getCode()], "§7" + category.getKorName() + " 경험치를 얻을 수 있습니다", "",
                    "§f다음 레벨까지: §b" + String.format("%.2f", manager.getProgress(target.getUniqueId(), category)) + "% §7(" + manager.getExpData(category).get(target.getUniqueId().toString()) + "/" + manager.getExpRequirements(manager.getLevel(target.getUniqueId(), category)) + ")",
                    "§6-".repeat(repeatCnt) + "§7-".repeat(20 - repeatCnt),
                    "",
                    "§b★ 순위표",
                    "§e#1 " + leaders[0], "§6#2 " + leaders[1], "§c#3 " + leaders[2]
                    ));
        }

        List<Map.Entry<String, Integer>> totalLevelLeaderBoard = new ArrayList<>();
        for (String uuid : manager.getLevelData(LevelManager.ExpCat.COMBAT).keySet()) {
            totalLevelLeaderBoard.add(new AbstractMap.SimpleEntry<>(uuid, manager.getTotalLevel(UUID.fromString(uuid))));
        }

        for (int i = 0; i < totalLevelLeaderBoard.size() - 1; i++) {
            for (int j = i + 1; j < totalLevelLeaderBoard.size(); j++) {
                int left = totalLevelLeaderBoard.get(i).getValue();
                int right = totalLevelLeaderBoard.get(j).getValue();
                if (left > right) continue;
                Map.Entry<String, Integer> temp = totalLevelLeaderBoard.get(i);
                totalLevelLeaderBoard.set(i, totalLevelLeaderBoard.get(j));
                totalLevelLeaderBoard.set(j, temp);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (i >= totalLevelLeaderBoard.size()) continue;

            OfflinePlayer leader_i = Bukkit.getOfflinePlayer(UUID.fromString(totalLevelLeaderBoard.get(i).getKey()));

            ItemStack leaderHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta leaderMeta = (SkullMeta) playerHead.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add("§e★§f 레벨: §e" + totalLevelLeaderBoard.get(i).getValue());
            int ticks_played = leader_i.getStatistic(Statistic.PLAY_ONE_MINUTE);
            lore.add("§b⌚§f 접속 시간: §b" + ticks_played / 72000 + "시간 " + (ticks_played % 72000) / 1200 + "분");
            leaderMeta.setOwningPlayer(leader_i);
            leaderMeta.setLore(lore);

            leaderMeta.setDisplayName("§6⬛전체 #" + (i + 1) + " " + leader_i.getName());
            leaderHead.setItemMeta(leaderMeta);
            inv.setItem(38 + i, leaderHead);
        }

        player.openInventory(inv);
        return true;
    }

    public ItemStack createItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(name);
        meta.setLore(List.of(lore));
        item.setItemMeta(meta);
        return item;
    }
}
