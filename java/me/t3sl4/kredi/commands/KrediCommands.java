package me.t3sl4.kredi.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import me.t3sl4.kredi.T3SL4Kredi;
import me.t3sl4.kredi.util.KrediAPI;
import me.t3sl4.kredi.util.MessageUtil;
import me.t3sl4.kredi.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KrediCommands implements CommandExecutor {
    SettingsManager manager = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kredi")) {
            if (args.length == 0) {
                sender.sendMessage(MessageUtil.INFO_LINE_1);
                sender.sendMessage(MessageUtil.INFO_LINE_2);
                if (sender.isOp()) {
                    sender.sendMessage(MessageUtil.INFO_LINE_3);
                    sender.sendMessage(MessageUtil.INFO_LINE_4);
                    sender.sendMessage(MessageUtil.INFO_LINE_5);
                    sender.sendMessage(MessageUtil.INFO_LINE_6);
                }
                return true;
            }
            if (args.length == 1 && !args[0].equalsIgnoreCase("reload") &&
                    !args[0].equalsIgnoreCase("yeni") &&
                    !args[0].equalsIgnoreCase("+") &&
                    !args[0].equalsIgnoreCase("-")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(MessageUtil.PLAYER_NOT_FOUND.replaceAll("%player%", args[0]));
                    return true;
                }
                sender.sendMessage(MessageUtil.SHOW_CREDIT
                        .replaceAll("%player%", target.getName())
                        .replaceAll("%kredi%", String.valueOf(KrediAPI.getKredi(target.getUniqueId().toString()))));
            }
            if (args[0].equalsIgnoreCase("+")) {
                int ekle;
                if (!sender.isOp()) {
                    sender.sendMessage(MessageUtil.NO_PERM_MESSAGE);
                    return false;
                }
                Player p = Bukkit.getPlayer(args[1]);
                if (p == null) {
                    sender.sendMessage(MessageUtil.PLAYER_NOT_FOUND.replaceAll("%player%", args[1]));
                    return true;
                }
                try {
                    ekle = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    sender.sendMessage(MessageUtil.ERROR_MESSAGE);
                    return true;
                }
                KrediAPI.addKredi(p.getUniqueId().toString(), ekle);
                p.sendMessage(MessageUtil.CREDI_ADDED_YOUR
                        .replaceAll("%kredi%", "" + ekle)
                        .replaceAll("%toplam_kredi%", "" + KrediAPI.getKredi(p.getUniqueId().toString())));
                if (sender instanceof Player &&
                        !Objects.equals(p.getUniqueId().toString(), ((Player)sender).getUniqueId().toString()))
                    sender.sendMessage(MessageUtil.CREDI_ADDED_OTHER.replaceAll("%player%", p.getName()).replaceAll("%kredi%", "" + ekle));
            }
            if (args[0].equalsIgnoreCase("-")) {
                int eksilt;
                if (!sender.isOp()) {
                    sender.sendMessage(MessageUtil.NO_PERM_MESSAGE);
                    return false;
                }
                Player p = Bukkit.getPlayer(args[1]);
                if (p == null) {
                    sender.sendMessage(MessageUtil.PLAYER_NOT_FOUND.replaceAll("%player%", args[1]));
                    return true;
                }
                try {
                    eksilt = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    sender.sendMessage(MessageUtil.ERROR_MESSAGE);
                    return true;
                }
                KrediAPI.removeKredi(p.getUniqueId().toString(), eksilt);
                p.sendMessage(MessageUtil.CREDI_TAKEN_YOUR.replaceAll("%kredi%", "" + eksilt).replaceAll("%toplam_kredi%", "" + KrediAPI.getKredi(p.getUniqueId().toString())).replace("%kredi%", "" + eksilt));
                sender.sendMessage(MessageUtil.CREDI_TAKEN_OTHER.replaceAll("%player%", p.getName()).replaceAll("%kredi%", "" + eksilt));
            }
            if (args[0].equalsIgnoreCase("yeni")) {
                if (!sender.isOp()) {
                    sender.sendMessage(MessageUtil.NO_PERM_MESSAGE);
                    return false;
                }
                if (args.length < 4) {
                    sender.sendMessage(MessageUtil.PREFIX + "/kredi yeni <komut ismi> <fiyat> <komut> ");
                    return true;
                }
                ArrayList<String> arguments = new ArrayList<>();
                arguments.addAll(Arrays.asList(args));
                String first = "";
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++)
                    sb.append(args[i] + " ");
                String command = "/" + label + " " + sb.toString();
                String real = ArgumentDecomposition.getRealCommand(command);
                String shown = ArgumentDecomposition.getShownCommnad(command);
                int price = ArgumentDecomposition.getPrice(command).intValue();
                if (real == null || real.equalsIgnoreCase("") || shown == null || shown
                        .equalsIgnoreCase("") || price < 0) {
                    sender.sendMessage(MessageUtil.ERROR_MESSAGE);
                    return true;
                }
                ArrayList<String> reals = new ArrayList<>(Arrays.asList(new String[] { real }));
                sender.sendMessage(MessageUtil.COMMAND_ADDED);
                this.manager.commands.getConfig().set("Komutlar." + shown + ".Kredi", Integer.valueOf(price));
                this.manager.commands.save();
                this.manager.commands.getConfig().set("Komutlar." + shown + ".Komut", reals);
                this.manager.commands.save();
                this.manager.commands.getConfig().set("Komutlar." + shown + ".Onay", Boolean.valueOf(false));
                this.manager.commands.save();
                this.manager.commands.getConfig().set("Komutlar." + shown + ".TekSeferlik", Boolean.valueOf(false));
                this.manager.commands.save();
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.isOp()) {
                    sender.sendMessage(MessageUtil.NO_PERM_MESSAGE);
                    return false;
                }
                this.manager.config.load();
                MessageUtil.loadMessages();
                this.manager.commands.load();
                this.manager.data.load();
                this.manager.onaymenusu.load();
                sender.sendMessage(MessageUtil.CONFIG_RELOADED);
                return true;
            }
        }
        return true;
    }
}
