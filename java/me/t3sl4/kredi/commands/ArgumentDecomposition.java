package me.t3sl4.kredi.commands;

import org.bukkit.Bukkit;

public class ArgumentDecomposition {
    public static Integer getPrice(String text) {
        int first = text.indexOf("'");
        int second = text.indexOf("'", first + 1);
        int third = text.indexOf("'", second + 1);
        String shown1 = text.substring(0, third);
        String shown2 = shown1.replace("/kredi", "").replace("yeni", "").trim();
        String price1 = shown2.substring(shown2.indexOf("'") + 1);
        String price2 = price1.substring(price1.indexOf("'") + 1).trim();
        int price = -1;
        try {
            price = Integer.parseInt(price2);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Komut olubir sorun olu! (AD-20)");
            return Integer.valueOf(-1);
        }
        return Integer.valueOf(price);
    }

    public static String getRealCommand(String text) {
        String real1 = text.substring(text.indexOf("'") + 1);
        String real2 = real1.substring(real1.indexOf("'") + 1).trim();
        String real3 = real2.substring(real2.indexOf("'") + 1).replace("'", "");
        return real3;
    }

    public static String getShownCommnad(String text) {
        String real3 = getRealCommand(text);
        int price = getPrice(text).intValue();
        String price2 = String.valueOf(price);
        String real = text.replace(real3, "").replace(price2, "").replace("/kredi", "").replace("yeni", "").replace("'", "").trim();
        return real;
    }
}
