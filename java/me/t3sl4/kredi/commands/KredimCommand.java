package me.t3sl4.kredi.commands;

import me.t3sl4.kredi.util.KrediAPI;
import me.t3sl4.kredi.util.MessageUtil;
import me.t3sl4.kredi.util.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KredimCommand implements CommandExecutor {
    SettingsManager manager = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kredim")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Konsol bu komudu kullanamaz !");
                return true;
            }
            sender.sendMessage(MessageUtil.SHOW_CREDIT_SELF.replaceAll("%kredi%", String.valueOf(KrediAPI.getKredi(((Player)sender).getUniqueId().toString()))));
        }
        return true;
    }
}
