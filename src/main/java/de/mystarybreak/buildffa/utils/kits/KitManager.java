package de.mystarybreak.buildffa.utils.kits;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class KitManager {

    private BuildFFA plugin;

    private Kit currentKit;

    private ArrayList<Kit> kitPool;

    private int seconds = 0;
    private int minutes = 3;
    private int taskID;

    public KitManager(BuildFFA plugin) {
        this.plugin = plugin;
        this.kitPool = new ArrayList<>();
        kitPool.add(new AnglerKit(plugin));
        kitPool.add(new KnockbackKit(plugin));
        kitPool.add(new EndermanKit(plugin));
        startKitChange();
    }

    public void startKitChange() {
        if (plugin.getMapManager().getMapPool().isEmpty()) return;
        Bukkit.getScheduler().cancelTask(taskID);
        changeKit();
        seconds = 0;
        minutes = 3;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (seconds == 0) {
                    if (minutes == 0) {
                        seconds = 0;
                        minutes = 3;
                        changeKit();
                        Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Das neue Kit ist nun das §a" + currentKit.getScoreboardName() + " §7Kit.");
                    } else {
                        seconds = 59;
                        minutes--;
                    }
                } else
                    seconds--;
            }
        }, 0, 20);
    }

    private void changeKit() {
        Random r = new Random();
        Kit newKit = kitPool.get(r.nextInt(kitPool.size() - 1));
        while (newKit == currentKit)
            newKit = kitPool.get(r.nextInt(kitPool.size() - 1));

        currentKit = newKit;

        plugin.getScoreboardManager().updateScoreboardForAll();
        for (Player a : Bukkit.getOnlinePlayers()) {
            a.getInventory().setContents(currentKit.getContents());
            a.getInventory().setArmorContents(currentKit.getArmorContents());
        }
    }

    public ArrayList<Kit> getKitPool() {
        return kitPool;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public Kit getCurrentKit() {
        return currentKit;
    }
}
