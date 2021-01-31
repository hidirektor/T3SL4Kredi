package me.t3sl4.kredi.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAPI {
    private File configFile;

    private String filename;

    private JavaPlugin plugin;

    private boolean shouldCopy;

    private FileConfiguration fileConfiguration;

    public ConfigAPI(JavaPlugin plugin, String filename, Boolean shouldCopy) {
        this.filename = filename + ".yml";
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), this.filename);
        this.shouldCopy = shouldCopy.booleanValue();
        if (shouldCopy.booleanValue())
            firstRun(plugin);
        this.fileConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
        load();
    }

    public FileConfiguration getConfig() {
        load();
        return this.fileConfiguration;
    }

    public void save() {
        try {
            this.fileConfiguration.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            this.fileConfiguration.load(this.configFile);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            if (this.shouldCopy)
                firstRun(this.plugin);
            this.fileConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void firstRun(JavaPlugin plugin) {
        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            copy(plugin.getResource(this.filename), this.configFile);
        }
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[63];
            int len;
            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            in.close();
        } catch (Exception exception) {}
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return getConfig().getConfigurationSection(path);
    }
}
