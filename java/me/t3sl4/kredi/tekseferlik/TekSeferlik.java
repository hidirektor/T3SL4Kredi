package me.t3sl4.kredi.tekseferlik;

import me.t3sl4.kredi.util.SettingsManager;

import java.io.Serializable;
import java.util.ArrayList;

public class TekSeferlik implements Serializable {
    private static final long serialVersionUID = 7938550555256151351L;

    String uuid;

    ArrayList<String> komutlar = new ArrayList<>();

    public TekSeferlik(String uuid) {
        this.uuid = uuid;
        (SettingsManager.getInstance()).tekSeferlik.add(this);
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ArrayList<String> getKomutlar() {
        return this.komutlar;
    }

    public void setKomutlar(ArrayList<String> komutlar) {
        this.komutlar = komutlar;
    }

    public void addKomutlar(String komutlar) {
        this.komutlar.add(komutlar);
    }
}
