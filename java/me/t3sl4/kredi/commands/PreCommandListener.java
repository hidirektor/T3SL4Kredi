package me.t3sl4.kredi.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import me.t3sl4.kredi.T3SL4Kredi;
import me.t3sl4.kredi.tekseferlik.TekSeferlik;
import me.t3sl4.kredi.onay.Onay;
import me.t3sl4.kredi.onay.OnayMenuItems;
import me.t3sl4.kredi.onay.OnayTask;
import me.t3sl4.kredi.util.KrediAPI;
import me.t3sl4.kredi.util.MessageUtil;
import me.t3sl4.kredi.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PreCommandListener implements Listener {
    SettingsManager manager = SettingsManager.getInstance();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        String command = e.getMessage();
        ConfigurationSection section = this.manager.commands.getConfigurationSection("Komutlar");
        for (String sec : section.getKeys(false)) {
            if (sec.equalsIgnoreCase(command)) {
                boolean gecebilir;
                if (p.getOpenInventory() != null)
                    p.closeInventory();
                boolean tekseferlik = section.getBoolean(sec + ".TekSeferlik");
                if (tekseferlik) {
                    if (KrediAPI.getStringToTekSeferlik(command)) {
                        gecebilir = false;
                    } else {
                        gecebilir = true;
                    }
                } else {
                    gecebilir = true;
                }
                if (gecebilir) {
                    List<String> realCommands = section.getStringList(sec + ".Komut");
                    ArrayList<String> realCommand1 = new ArrayList<>();
                    for (String realCommand : realCommands) {
                        if (realCommand.startsWith("/"))
                            realCommand = realCommand.substring(1, realCommand.length());
                        if (realCommand.contains("%player%"))
                            realCommand = realCommand.replace("%player%", p.getName());
                        realCommand1.add(realCommand);
                    }
                    int price = section.getInt(sec + ".Kredi");
                    boolean onaybool = section.getBoolean(sec + ".Onay");
                    e.setCancelled(true);
                    if (onaybool) {
                        if (KrediAPI.getStringToOnay(uuid) != null) {
                            if (KrediAPI.getStringToOnayTask(uuid, command) != null) {
                                b(p, realCommand1, command, e, Integer.valueOf(price));
                                continue;
                            }
                            a(p, price, realCommand1, command, e);
                            KrediAPI.getStringToOnay(uuid).addOnayTasks(new OnayTask(uuid, command));
                            continue;
                        }
                        Onay onay = new Onay(uuid);
                        if (KrediAPI.getStringToOnayTask(uuid, command) != null) {
                            a(p, price, realCommand1, command, e);
                            OnayTask task = KrediAPI.getStringToOnayTask(uuid, command);
                            onay.remOnayTasks(task);
                            onay.addOnayTasks(new OnayTask(uuid, command));
                            continue;
                        }
                        a(p, price, realCommand1, command, e);
                        KrediAPI.getStringToOnay(uuid).addOnayTasks(new OnayTask(uuid, command));
                        continue;
                    }
                    a(p, price, realCommand1, command, e);
                    continue;
                }
                p.sendMessage(MessageUtil.TEKSEFERLIK_KOMUT);
                e.setCancelled(true);
            }
        }
    }

    private HashMap<String, String> cmd = new HashMap<>();

    private HashMap<String, Integer> prc = new HashMap<>();

    private HashMap<String, ArrayList<String>> realCmd = new HashMap<>();

    private HashMap<String, PlayerCommandPreprocessEvent> event = new HashMap<>();

    @EventHandler
    public void c(InventoryClickEvent e) {
        if (e.getCurrentItem() == null)
            return;
        if (e.getCurrentItem().getType().equals(Material.AIR))
            return;
        if (Objects.equals(e.getInventory().getTitle(), MessageUtil.ONAY_MENU_TITLE))
            e.setCancelled(true);
        if (Objects.equals(e.getClickedInventory().getTitle(), MessageUtil.ONAY_MENU_TITLE)) {
            e.setCancelled(true);
            Player p = (Player)e.getWhoClicked();
            String uuid = p.getUniqueId().toString();
            ItemStack item = e.getCurrentItem();
            OnayMenuItems onayMenuItem = null;
            for (OnayMenuItems onayMenuItems : MessageUtil.ITEMS) {
                if (onayMenuItems.getItemStack() != null &&
                        onayMenuItems.getItemStack().isSimilar(item)) {
                    onayMenuItem = onayMenuItems;
                    break;
                }
            }
            if (onayMenuItem != null)
                if (Objects.equals(onayMenuItem.getOlay(), "onayla")) {
                    p.closeInventory();
                    a(p, ((Integer)this.prc.get(uuid)).intValue(), this.realCmd.get(uuid), this.cmd.get(uuid), this.event.get(uuid));
                    Onay onay = new Onay(uuid);
                    OnayTask task = KrediAPI.getStringToOnayTask(uuid, this.cmd.get(uuid));
                    onay.remOnayTasks(task);
                    onay.addOnayTasks(new OnayTask(uuid, this.cmd.get(uuid)));
                } else if (Objects.equals(onayMenuItem.getOlay(), "onaylama")) {
                    p.closeInventory();
                }
        }
    }

    public void b(Player p, ArrayList<String> realCommand1, String command, PlayerCommandPreprocessEvent e, Integer price) {
        Inventory inv = Bukkit.createInventory(null, 9, T3SL4Kredi.chatcolor(this.manager.onaymenusu.getConfig().getString("menuismi")));
        for (OnayMenuItems onayMenuItems : MessageUtil.ITEMS) {
            if (onayMenuItems.getItemStack() != null) {
                List<String> templore = new ArrayList<>();
                for (String s : onayMenuItems.getLore())
                    templore.add(s.replace("%komutlar%", realCommand1.toString()));
                onayMenuItems.setLore(templore);
                inv.setItem(onayMenuItems.getSlot(), onayMenuItems.getItemStack());
            }
        }
        this.cmd.put(p.getUniqueId().toString(), command);
        this.prc.put(p.getUniqueId().toString(), price);
        this.realCmd.put(p.getUniqueId().toString(), realCommand1);
        this.event.put(p.getUniqueId().toString(), e);
        p.openInventory(inv);
    }

    public void a(Player p, int price, ArrayList<String> realCommand1, String command, PlayerCommandPreprocessEvent e) {
        if (KrediAPI.getKredi(p.getUniqueId().toString()) < price) {
            p.sendMessage(MessageUtil.NOT_ENOUGH_CREDIT.replace("%kredi%", price + ""));
            e.setCancelled(true);
            return;
        }
        KrediAPI.removeKredi(p.getUniqueId().toString(), price);
        p.sendMessage(MessageUtil.CREDI_TAKEN_YOUR.replace("%kredi%", price + "").replace("%toplam_kredi%", KrediAPI.getKredi(p.getUniqueId().toString()) + ""));
        for (String rc : realCommand1)
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), rc);
        TekSeferlik tekSeferlik = KrediAPI.getStringToTekSeferlikOyuncu(p.getUniqueId().toString());
        tekSeferlik.addKomutlar(command);
        e.setCancelled(true);
    }
}
