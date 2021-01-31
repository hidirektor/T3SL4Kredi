package me.t3sl4.kredi.placeholder;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.t3sl4.kredi.T3SL4Kredi;
import me.t3sl4.kredi.util.KrediAPI;
import org.bukkit.plugin.Plugin;

public class MVdWPlaceholder implements PlaceholderReplacer {
    public MVdWPlaceholder() {
        PlaceholderAPI.registerPlaceholder((Plugin) T3SL4Kredi.getPlugin(), "kredi_miktar", this);
    }

    public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
        if (e.getPlayer() == null)
            return "Player needed!";
        return String.valueOf(KrediAPI.getKredi(e.getPlayer().getUniqueId().toString()));
    }
}
