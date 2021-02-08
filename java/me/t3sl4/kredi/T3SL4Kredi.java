package me.t3sl4.kredi;

import me.t3sl4.kredi.placeholder.MVdWPlaceholder;
import me.t3sl4.kredi.placeholder.PAPIPlaceholder;
import me.t3sl4.kredi.util.MessageUtil;
import me.t3sl4.kredi.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class T3SL4Kredi extends JavaPlugin {
    SettingsManager manager = SettingsManager.getInstance();

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("   ");
        Bukkit.getConsoleSender().sendMessage("  ____   __   __  _   _   _____   _____   ____    _       _  _   ");
        Bukkit.getConsoleSender().sendMessage(" / ___|  \\ \\ / / | \\ | | |_   _| |___ /  / ___|  | |     | || |  ");
        Bukkit.getConsoleSender().sendMessage(" \\___ \\   \\ V /  |  \\| |   | |     |_ \\  \\___ \\  | |     | || |_ ");
        Bukkit.getConsoleSender().sendMessage("  ___) |   | |   | |\\  |   | |    ___) |  ___) | | |___  |__   _|");
        Bukkit.getConsoleSender().sendMessage(" |____/    |_|   |_| \\_|   |_|   |____/  |____/  |_____|    |_|  ");
        Bukkit.getConsoleSender().sendMessage("    ");
		Bukkit.getConsoleSender().sendMessage("T3SL4 Series: T3SL4Kredi");
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") || Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&e[Kredi] &aPlaceholder Destegi Aktif Edildi"));
            if(MessageUtil.PLACEHOLDER_SUPPORT && Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                new MVdWPlaceholder();
                Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&e[Kredi] &cMVdWPlaceholder tespit edildi!"));
                Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&e[Kredi] &cPlaceholders: &e[ {kredi_miktar} ]"));
            } else if(!MessageUtil.PLACEHOLDER_SUPPORT && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                new PAPIPlaceholder(this).register();
                Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&e[Kredi] &cPlaceholderAPI tespit edildi!"));
                Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&e[Kredi] &cPlaceholders: &e[ %kredi_miktar% ]"));
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&e[Kredi] &cPlaceholder yuklu olmadigindan bazi ozellikler devre disi"));
        }
        this.manager.setup(this);
    }

    public void onDisable() {
        this.manager.stop();
    }

    public static String chatcolor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static T3SL4Kredi getPlugin() {
        return (T3SL4Kredi)Bukkit.getPluginManager().getPlugin("T3SL4Kredi");
    }
}
