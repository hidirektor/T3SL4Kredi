package me.t3sl4.kredi.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.t3sl4.kredi.T3SL4Kredi;
import me.t3sl4.kredi.util.KrediAPI;
import me.t3sl4.kredi.util.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PAPIPlaceholder extends PlaceholderExpansion {
    SettingsManager manager = SettingsManager.getInstance();
    private T3SL4Kredi p;

    public PAPIPlaceholder(Plugin p) {
        this.p = (T3SL4Kredi)p;
    }

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null)
            return "";
        if (identifier.equals("miktar"))
            return String.valueOf(KrediAPI.getKredi(p.getUniqueId().toString()));
        return null;
    }

    public String getAuthor() {
        return p.getDescription().getAuthors().toString();
    }

    public String getIdentifier() {
        return "kredi";
    }

    public String getVersion() {
        return p.getDescription().getVersion();
    }
}
