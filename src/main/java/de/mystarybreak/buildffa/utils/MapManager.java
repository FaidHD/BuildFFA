package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MapManager {

    private BuildFFA plugin;

    private ArrayList<Map> mapPool;

    private File file;
    private FileConfiguration cfg;

    private int taskID;
    private int minutes = 5;
    private int seconds = 0;


    private Map currentMap;

    public MapManager(BuildFFA plugin) {
        this.plugin = plugin;
        this.mapPool = new ArrayList<>();
        loadMaps();
    }

    public void addMap(Map map) {
        cfg.set(map.getName() + ".SpawnPoint", new LocationManager().getLocAsString(map.getSpawnPoint()));
        cfg.set(map.getName() + ".DeathHeight", map.getDeathHeight());
        cfg.set(map.getName() + ".PvPHeight", map.getPvpHeight());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeMap(String name) {
        cfg.set(name, null);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMaps() {
        Bukkit.getScheduler().cancelTask(taskID);
        mapPool.clear();
        this.file = new File("plugins/BuildFFA", "maps.yml");
        this.cfg = YamlConfiguration.loadConfiguration(file);

        for (String s : cfg.getKeys(false)) {
            mapPool.add(new Map(s, cfg.getString(s + ".Builder"), new LocationManager().getStringAsLoc(cfg.getString(s + ".SpawnPoint")), cfg.getInt(s + ".DeathHeight"), cfg.getInt(s + ".PvPHeight"), plugin));
        }
        startMapChange();
    }

    private void startMapChange() {
        if (mapPool.size() == 0)
            Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Es sind keine Maps eingerichtet");
        else if (mapPool.size() == 1) {
            currentMap = mapPool.get(0);
            currentMap.startBlockReplace();
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        a.teleport(currentMap.getSpawnPoint());
                    }
                }
            }.runTaskLater(plugin, 2);
        } else {
            seconds = 0;
            minutes = 5;
            Random r = new Random();
            plugin.getScoreboardManager().updateScoreboardForAll();
            currentMap = mapPool.get(r.nextInt(mapPool.size() - 1));
            currentMap.startBlockReplace();
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (seconds == 0) {
                        if (minutes == 0) {
                            seconds = 0;
                            minutes = 5;
                            changeMap();
                        } else {
                            seconds = 59;
                            minutes--;
                        }
                    } else
                        seconds--;
                    if (minutes == 0) {
                        switch (seconds) {
                            case 10:
                                Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Die Map wird in §e10 Sekunden §7gewechselt.");
                                Bukkit.getOnlinePlayers().forEach(a -> {
                                    a.playSound(a.getLocation(), Sound.NOTE_BASS, 1, 0);
                                });
                                break;
                            case 5:
                                Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Die Map wird in §e5 Sekunden §7gewechselt.");
                                Bukkit.getOnlinePlayers().forEach(a -> {
                                    a.playSound(a.getLocation(), Sound.NOTE_BASS, 1, 0);
                                });
                                break;
                            case 4:
                                Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Die Map wird in §e4 Sekunden §7gewechselt.");
                                Bukkit.getOnlinePlayers().forEach(a -> {
                                    a.playSound(a.getLocation(), Sound.NOTE_BASS, 1, 0);
                                });
                                break;
                            case 3:
                                Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Die Map wird in §e3 Sekunden §7gewechselt.");
                                Bukkit.getOnlinePlayers().forEach(a -> {
                                    a.playSound(a.getLocation(), Sound.NOTE_BASS, 1, 0);
                                });
                                break;
                            case 2:
                                Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Die Map wird in §e2 Sekunden §7gewechselt.");
                                Bukkit.getOnlinePlayers().forEach(a -> {
                                    a.playSound(a.getLocation(), Sound.NOTE_BASS, 1, 0);
                                });
                                break;
                            case 1:
                                Bukkit.broadcastMessage(plugin.getData().getPrefix() + "Die Map wird in §e1 Sekunde §7gewechselt.");
                                Bukkit.getOnlinePlayers().forEach(a -> {
                                    a.playSound(a.getLocation(), Sound.NOTE_BASS, 1, 0);
                                });
                                break;
                        }
                    }
                }
            }, 0, 20);
        }
    }

    private void changeMap() {
        Random r = new Random();
        Map newMap = mapPool.get(r.nextInt(mapPool.size() - 1));
        while (newMap == currentMap)
            newMap = mapPool.get(r.nextInt(mapPool.size() - 1));
        currentMap.blockHardReset();
        currentMap = newMap;
        currentMap.startBlockReplace();
        plugin.getScoreboardManager().updateScoreboardForAll();

        for (Player a : Bukkit.getOnlinePlayers())
            a.teleport(currentMap.getSpawnPoint());
    }

    public void addPlacedBlock(Block block) {
        currentMap.getPlacedBlocks().put(block.getLocation(), 120);
    }

    public boolean mapExists(String name) {
        for (Map map : mapPool)
            if (map.getName().equalsIgnoreCase(name))
                return true;

        return false;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public ArrayList<Map> getMapPool() {
        return mapPool;
    }

    public Map getCurrentMap() {
        return currentMap;
    }
}
