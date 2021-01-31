package me.t3sl4.kredi.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

import me.t3sl4.kredi.mysql.MySQL;
import me.t3sl4.kredi.tekseferlik.TekSeferlik;
import me.t3sl4.kredi.onay.Onay;
import me.t3sl4.kredi.onay.OnayTask;
import org.bukkit.Bukkit;

public class KrediAPI {
    static SettingsManager manager = SettingsManager.getInstance();

    public static OnayTask getStringToOnayTask(String uuid, String command) {
        for (Onay onay : (SettingsManager.getInstance()).onaytasks) {
            if (Objects.equals(onay.getUuid(), uuid))
                for (OnayTask onayTask : onay.getOnayTasks()) {
                    if (Objects.equals(onayTask.getCommand(), command))
                        return onayTask;
                }
        }
        return null;
    }

    public static Onay getStringToOnay(String uuid) {
        for (Onay onay : (SettingsManager.getInstance()).onaytasks) {
            if (Objects.equals(onay.getUuid(), uuid))
                return onay;
        }
        return null;
    }

    public static boolean getStringToTekSeferlik(String s) {
        for (TekSeferlik tekSeferlik : (SettingsManager.getInstance()).tekSeferlik) {
            if (tekSeferlik.getKomutlar() != null)
                for (String komut : tekSeferlik.getKomutlar()) {
                    if (Objects.equals(komut, s))
                        return true;
                }
        }
        return false;
    }

    public static TekSeferlik getStringToTekSeferlikOyuncu(String uuid) {
        for (TekSeferlik tekSeferlik : (SettingsManager.getInstance()).tekSeferlik) {
            if (Objects.equals(tekSeferlik.getUuid(), uuid))
                return tekSeferlik;
        }
        return new TekSeferlik(uuid);
    }

    private static boolean playerExists(UUID uuid) {
        if (manager.config.getConfig().getBoolean("mysql-enabled")) {
            try {
                ResultSet rs = MySQL.query("SELECT * FROM " + MySQL.tablename + " WHERE uuid= '" + uuid + "'");
                if (rs.next())
                    return (rs.getString("uuid") != null);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }
        if (manager.data.getConfig().get(uuid.toString() + ".kredi") == null) {
            manager.data.getConfig().set(uuid + ".kredi", Integer.valueOf(0));
            manager.data.save();
        }
        return true;
    }

    private static void createPlayer(UUID uuid) {
        if (!playerExists(uuid))
            MySQL.update("INSERT INTO " + MySQL.tablename + " (uuid, playername, kredimiktar) VALUES ('" + uuid + "', '" + Bukkit.getOfflinePlayer(uuid).getName() + "', '0');");
    }

    private static int getKredi(UUID uuid) {
        int i = 0;
        if (playerExists(uuid)) {
            try {
                ResultSet rs = MySQL.query("SELECT * FROM " + MySQL.tablename + " WHERE uuid= '" + uuid + "'");
                if (rs.next() && rs.getString("kredimiktar") != null)
                    i = rs.getInt("kredimiktar");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getKredi(uuid);
        }
        return i;
    }

    public static int getKredi(String uuid) {
        if (playerExists(UUID.fromString(uuid))) {
            if (manager.config.getConfig().getBoolean("mysql-enabled"))
                return getKredi(UUID.fromString(uuid));
            return manager.data.getConfig().getInt(uuid + ".kredi");
        }
        return 0;
    }

    public static void addKredi(String uuid, int amount) {
        setKredi(uuid, getKredi(uuid) + amount);
    }

    public static void removeKredi(String uuid, int amount) {
        setKredi(uuid, getKredi(uuid) - amount);
    }

    public static void setKredi(String uuid, int amount) {
        if (playerExists(UUID.fromString(uuid)))
            if (manager.config.getConfig().getBoolean("mysql-enabled")) {
                MySQL.update("UPDATE " + MySQL.tablename + " SET kredimiktar= '" + amount + "' WHERE uuid= '" + uuid + "';");
            } else {
                manager.data.getConfig().set(uuid + ".kredi", Integer.valueOf(amount));
                manager.data.save();
            }
    }
}
