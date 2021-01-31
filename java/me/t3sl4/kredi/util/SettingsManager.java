package me.t3sl4.kredi.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import me.t3sl4.kredi.*;
import me.t3sl4.kredi.commands.KrediCommands;
import me.t3sl4.kredi.commands.KredimCommand;
import me.t3sl4.kredi.commands.PreCommandListener;
import me.t3sl4.kredi.mysql.MySQL;
import me.t3sl4.kredi.onay.Onay;
import me.t3sl4.kredi.placeholder.PAPIPlaceholder;
import me.t3sl4.kredi.placeholder.MVdWPlaceholder;
import me.t3sl4.kredi.tekseferlik.TekSeferlik;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class SettingsManager {
    static SettingsManager instance = new SettingsManager();

    public ConfigAPI config;

    public ConfigAPI data;

    public ConfigAPI commands;

    public ConfigAPI onaymenusu;

    private T3SL4Kredi tkredi;

    public ArrayList<Onay> onaytasks = new ArrayList<>();

    public ArrayList<TekSeferlik> tekSeferlik = new ArrayList<>();

    public static SettingsManager getInstance() {
        return instance;
    }

    public void setup(T3SL4Kredi tkredi) {
        this.tkredi = tkredi;
        try {
            File file = new File(tkredi.getDataFolder() + "/data/");
            file.mkdirs();
            this.onaytasks = SL.<ArrayList<Onay>>load(tkredi.getDataFolder() + "/data/zamanlar.yml");
            this.tekSeferlik = SL.<ArrayList<TekSeferlik>>load(tkredi.getDataFolder() + "/data/tekseferlik.yml");
        } catch (Exception e) {
            try {
                SL.save(this.onaytasks, tkredi.getDataFolder() + "/data/zamanlar.yml");
                SL.save(this.tekSeferlik, tkredi.getDataFolder() + "/data/tekseferlik.yml");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        this.config = new ConfigAPI(T3SL4Kredi.getPlugin(), "ayarlar", Boolean.valueOf(true));
        this.data = new ConfigAPI(T3SL4Kredi.getPlugin(), "data", Boolean.valueOf(true));
        this.commands = new ConfigAPI(T3SL4Kredi.getPlugin(), "komutlar", Boolean.valueOf(true));
        this.onaymenusu = new ConfigAPI(T3SL4Kredi.getPlugin(), "onaymenusu", Boolean.valueOf(true));
        if (this.commands.getConfigurationSection("Komutlar") == null) {
            this.commands.getConfig().createSection("Komutlar");
            this.commands.save();
        }
        if (this.config.getConfig().getBoolean("mysql-enabled")) {
            MySQL.readMySQL();
            MySQL.connect();
            MySQL.createTable();
        }
        registerCommands();
        registerListener(new Listener[] { new PreCommandListener() });
        MessageUtil.loadMessages();
    }

    public void stop() {
        try {
            File file = new File(this.tkredi.getDataFolder() + "/data/");
            file.mkdirs();
            SL.save(this.onaytasks, this.tkredi.getDataFolder() + "/data/zamanlar.yml");
            SL.save(this.tekSeferlik, this.tkredi.getDataFolder() + "/data/tekseferlik.yml");
        } catch (Exception exception) {}
        if (this.config.getConfig().getBoolean("mysql-enabled"))
            MySQL.close();
    }

    private void registerCommands() {
        this.tkredi.getCommand("kredi").setExecutor(new KrediCommands());
        this.tkredi.getCommand("kredim").setExecutor(new KredimCommand());
    }

    private void registerListener(Listener... listeners) {
        Arrays.<Listener>stream(listeners).forEach(listener -> this.tkredi.getServer().getPluginManager().registerEvents(listener, (Plugin)this.tkredi));
    }
}
