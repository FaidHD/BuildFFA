package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;

public class ActionbarManager {

    private BuildFFA plugin;

    public ActionbarManager(BuildFFA plugin) {
        this.plugin = plugin;
        startActionBarTimer();
    }

    private void startActionBarTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                updateActionBar();
            }
        }, 0, 20);
    }

    public void updateActionBar() {
        int kitSec = plugin.getKitManager().getSeconds();
        int kitMin = plugin.getKitManager().getMinutes();

        int mapSec = plugin.getMapManager().getSeconds();
        int mapMin = plugin.getMapManager().getMinutes();

        String secKit;
        if (kitSec < 10)
            secKit = "0" + kitSec;
        else
            secKit = "" + kitSec;

        String secMap;
        if (mapSec < 10)
            secMap = "0" + mapSec;
        else
            secMap = "" + mapSec;

        String actionbar = "§2KitChange §7in §a" + kitMin + "§8:§a" + secKit + "  §8§l✘  §2Mapchange §7in §a" + mapMin + "§8:§a" + secMap;
        plugin.getData().sendAllActionBar(actionbar);
    }
}
