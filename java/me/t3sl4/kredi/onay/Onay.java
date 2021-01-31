package me.t3sl4.kredi.onay;

import me.t3sl4.kredi.util.SettingsManager;

import java.io.Serializable;
import java.util.ArrayList;

public class Onay implements Serializable {
    private static final long serialVersionUID = -7370502603885949634L;

    private String uuid;

    private ArrayList<OnayTask> onayTasks = new ArrayList<>();

    public Onay(String uuid, int i) {
        this.uuid = uuid;
        (SettingsManager.getInstance()).onaytasks.add(this);
    }

    public Onay(String uuid) {
        this.uuid = uuid;
        (SettingsManager.getInstance()).onaytasks.add(this);
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ArrayList<OnayTask> getOnayTasks() {
        return this.onayTasks;
    }

    public void setOnayTasks(ArrayList<OnayTask> onayTasks) {
        this.onayTasks = onayTasks;
    }

    public void addOnayTasks(OnayTask onayTasks) {
        this.onayTasks.add(onayTasks);
    }

    public void remOnayTasks(OnayTask onayTasks) {
        this.onayTasks.remove(onayTasks);
    }
}
