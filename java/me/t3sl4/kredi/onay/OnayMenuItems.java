package me.t3sl4.kredi.onay;

import java.util.ArrayList;
import java.util.List;

import me.t3sl4.kredi.T3SL4Kredi;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnayMenuItems {
    int slot = 0;

    String olay = "";

    int id = 0;

    int data = 0;

    String name = "";

    List<String> lore = new ArrayList<>();

    public ItemStack getItemStack() {
        if (this.id != 0 && Material.getMaterial(this.id) != null) {
            ItemStack item = new ItemStack(Material.getMaterial(this.id), 1, (short)0, Byte.valueOf((byte)this.data));
            ItemMeta m = item.getItemMeta();
            m.setDisplayName(T3SL4Kredi.chatcolor(this.name));
            m.setLore(this.lore);
            item.setItemMeta(m);
            return item;
        }
        return null;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getOlay() {
        return this.olay;
    }

    public void setOlay(String olay) {
        this.olay = olay;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
