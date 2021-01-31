package me.t3sl4.kredi.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.t3sl4.kredi.T3SL4Kredi;
import me.t3sl4.kredi.util.SettingsManager;
import org.bukkit.Bukkit;

public class MySQL {
    public static String tablename;

    private static String host;

    private static String port;

    private static String db;

    private static String user;

    private static String pw;

    private static Connection con;

    public MySQL(String host, String port, String db, String user, String pw) {}

    private static boolean isConnected() {
        return (con != null);
    }

    static SettingsManager manager = SettingsManager.getInstance();

    public static void connect() {
        if (!isConnected())
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + pw + "&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10");
                Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&a(!) &eMySQL Info: &aENABLED"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close() {
        if (isConnected())
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage(T3SL4Kredi.chatcolor("&a(!) &eMySQL Info: &cDISABLED"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static ResultSet query(String q) {
        ResultSet rs = null;
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void update(String q) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(q);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        if (isConnected())
            update("CREATE TABLE IF NOT EXISTS " + tablename + " (uuid VARCHAR(100), playername VARCHAR(16), kredimiktar int)");
    }

    public static void readMySQL() {
        host = manager.config.getConfig().getString("MySQL.host");
        port = manager.config.getConfig().getString("MySQL.port");
        db = manager.config.getConfig().getString("MySQL.database");
        db = manager.config.getConfig().getString("MySQL.tablename");
        user = manager.config.getConfig().getString("MySQL.user");
        pw = manager.config.getConfig().getString("MySQL.password");
    }
}
