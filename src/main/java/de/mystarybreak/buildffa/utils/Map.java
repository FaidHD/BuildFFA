package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class Map {

    private BuildFFA plugin;

    private String name;
    private String builder;

    private Location spawnPoint;
    private int deathHeight;
    private int pvpHeight;

    private HashMap<Location, Integer> placedBlocks;
    private int taskID;

    public Map(String name) {
        this.name = name;
    }

    public Map(String name, String builder, Location spawnPoint, int deathHeight, int pvpHeight, BuildFFA plugin) {
        this.plugin = plugin;
        this.name = name;
        this.builder = builder;
        this.spawnPoint = spawnPoint;
        this.deathHeight = deathHeight;
        this.pvpHeight = pvpHeight;
        placedBlocks = new HashMap<>();
    }

    public void startBlockReplace() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    for (java.util.Map.Entry<Location, Integer> entry : placedBlocks.entrySet()) {
                        Location location = entry.getKey();
                        Integer ticks = entry.getValue();

                        if (ticks == 60)
                            location.getBlock().setType(Material.REDSTONE_BLOCK);
                        if (ticks == 0) {
                            location.getBlock().setType(Material.AIR);
                            placedBlocks.remove(location);
                        } else
                            placedBlocks.put(location, ticks - 1);
                    }
                }catch (Exception e) {

                }
            }
        }, 0, 1);
    }

    public void blockHardReset() {
        if(placedBlocks.isEmpty()) return;
        Bukkit.getScheduler().cancelTask(taskID);
        for(Location location : placedBlocks.keySet()) {
            location.getBlock().setType(Material.AIR);
        }
        placedBlocks.clear();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public void setDeathHeight(int deathHeight) {
        this.deathHeight = deathHeight;
    }

    public void setPvpHeight(int pvpHeight) {
        this.pvpHeight = pvpHeight;
    }

    public String getName() {
        return name;
    }

    public String getBuilder() {
        return builder;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public int getDeathHeight() {
        return deathHeight;
    }

    public int getPvpHeight() {
        return pvpHeight;
    }

    public HashMap<Location, Integer> getPlacedBlocks() {
        return placedBlocks;
    }
}
