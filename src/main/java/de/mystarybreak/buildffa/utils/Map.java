package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;

public class Map {

    private BuildFFA plugin;

    @Getter private String name;
    @Setter @Getter private String builder;

    @Setter @Getter private Location spawnPoint;
    @Setter @Getter private int deathHeight;
    @Setter @Getter private int pvpHeight;

    @Getter private HashMap<Location, Integer> placedBlocks;
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
                for (java.util.Map.Entry<Location, Integer> entry : placedBlocks.entrySet()) {
                    Location location = entry.getKey();
                    Integer ticks = entry.getValue();

                    if(ticks == 60)
                        location.getBlock().setType(Material.REDSTONE_BLOCK);
                    if(ticks == 0) {
                        location.getBlock().setType(Material.AIR);
                        placedBlocks.remove(location);
                    }else
                        placedBlocks.put(location, ticks - 1);
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

}
