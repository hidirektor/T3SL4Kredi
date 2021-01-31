package me.t3sl4.kredi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.t3sl4.kredi.T3SL4Kredi;
import me.t3sl4.kredi.onay.OnayMenuItems;
import org.bukkit.Bukkit;

public class MessageUtil {
    public static String PREFIX;

    public static String CONFIG_RELOADED;

    public static String NO_PERM_MESSAGE;

    public static String PLAYER_NOT_FOUND;

    public static String ERROR_MESSAGE;

    public static String SHOW_CREDIT;

    public static String SHOW_CREDIT_SELF;

    public static String NOT_ENOUGH_CREDIT;

    public static String COMMAND_ADDED;

    public static String CREDI_ADDED_OTHER;

    public static String CREDI_ADDED_YOUR;

    public static String CREDI_TAKEN_OTHER;

    public static String CREDI_TAKEN_YOUR;

    public static String INFO_LINE_1;

    public static String INFO_LINE_2;

    public static String INFO_LINE_3;

    public static String INFO_LINE_4;

    public static String INFO_LINE_5;

    public static String INFO_LINE_6;

    public static String TEKSEFERLIK_KOMUT;

    public static int ONAY_ISTEME_SURESI;

    public static String ONAY_MENU_TITLE;

    public static boolean PLACEHOLDER_SUPPORT;

    public static ArrayList<OnayMenuItems> ITEMS = new ArrayList<>();

    static SettingsManager manager = SettingsManager.getInstance();

    public static void loadMessages() {
        PREFIX = T3SL4Kredi.chatcolor(manager.config.getConfig().getString("PREFIX"));
        ONAY_ISTEME_SURESI = manager.config.getConfig().getInt("ONAY_ISTEME_SURESI");
        ONAY_MENU_TITLE = T3SL4Kredi.chatcolor(manager.onaymenusu.getConfig().getString("menuismi"));
        CONFIG_RELOADED = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("CONFIG_RELOADED"));
        NO_PERM_MESSAGE = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("NO_PERM_MESSAGE"));
        PLAYER_NOT_FOUND = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("PLAYER_NOT_FOUND"));
        TEKSEFERLIK_KOMUT = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("TEKSEFERLIK_KOMUT"));
        ERROR_MESSAGE = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("ERROR_MESSAGE"));
        CREDI_ADDED_OTHER = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("CREDI_ADDED_OTHER"));
        SHOW_CREDIT = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("SHOW_CREDIT"));
        SHOW_CREDIT_SELF = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("SHOW_CREDIT_SELF"));
        NOT_ENOUGH_CREDIT = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("NOT_ENOUGH_CREDIT"));
        COMMAND_ADDED = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("COMMAND_ADDED"));
        CREDI_ADDED_YOUR = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("CREDI_ADDED_YOUR"));
        CREDI_TAKEN_OTHER = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("CREDI_TAKEN_OTHER"));
        CREDI_TAKEN_YOUR = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("CREDI_TAKEN_YOUR"));
        PLACEHOLDER_SUPPORT = manager.config.getConfig().getBoolean("PLACEHOLDER_SUPPORT");
        INFO_LINE_1 = T3SL4Kredi.chatcolor(manager.config.getConfig().getString("INFO_LINE_1"));
        INFO_LINE_2 = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("INFO_LINE_2"));
        INFO_LINE_3 = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("INFO_LINE_3"));
        INFO_LINE_4 = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("INFO_LINE_4"));
        INFO_LINE_5 = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("INFO_LINE_5"));
        INFO_LINE_6 = PREFIX + T3SL4Kredi.chatcolor(manager.config.getConfig().getString("INFO_LINE_6"));
        ArrayList<OnayMenuItems> test = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            test.add(new OnayMenuItems());
        for (String s : manager.onaymenusu.getConfigurationSection("").getKeys(false)) {
            if (!Objects.equals(s, "menuismi")) {
                int j = 298;
                try {
                    j = Integer.parseInt(s);
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&4Onay Menu Hata!"));
                    return;
                }
                if (manager.onaymenusu.getConfig().get(j + ".item") != null &&
                        manager.onaymenusu.getConfig().get(j + ".tiklayinca") != null &&
                        manager.onaymenusu.getConfig().get(j + ".lore") != null &&
                        manager.onaymenusu.getConfig().get(j + ".isim") != null) {
                    String item = manager.onaymenusu.getConfig().getString(j + ".item");
                    String tiklayinca = manager.onaymenusu.getConfig().getString(j + ".tiklayinca");
                    List<String> lore = manager.onaymenusu.getConfig().getStringList(j + ".lore");
                    String isim = manager.onaymenusu.getConfig().getString(j + ".isim");
                    String[] a = item.split(":");
                    int id = 0;
                    int data = 0;
                    if (a.length == 1) {
                        id = Integer.parseInt(a[0]);
                        data = 0;
                    }
                    if (a.length == 2) {
                        id = Integer.parseInt(a[0]);
                        data = Integer.parseInt(a[1]);
                    }
                    OnayMenuItems omi = test.get(j);
                    omi.setData(data);
                    omi.setId(id);
                    if (!lore.isEmpty())
                        if (lore.size() == 1) {
                            if (!Objects.equals(lore.get(0), ""))
                                omi.setLore(lore);
                        } else {
                            omi.setLore(lore);
                        }
                    omi.setName(isim);
                    omi.setSlot(j);
                    omi.setOlay(tiklayinca);
                    ITEMS.add(omi);
                }
            }
        }
    }
}
