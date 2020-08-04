package de.mystarybreak.buildffa.utils;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WorldContainer {

    private static final HashMap<String, WorldContainer> WORLD_CONTAINER_MAP = new HashMap<>();

    private final String name;
    private Location spawnLocation;

    public WorldContainer(String worldName) {
        this.name = worldName;
        WORLD_CONTAINER_MAP.put(worldName, this);
    }

    public String getName() {
        return this.name;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;
        new ConfigUtils().saveConfig();
    }

    public static HashMap<String, WorldContainer> getWorldContainerMap() {
        return WORLD_CONTAINER_MAP;
    }

    public void loadWorld() {
        Bukkit.createWorld(new WorldCreator(this.name));
    }

    public void deleteWorld() {
        Bukkit.unloadWorld(this.name, true);
        try {
            FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + "/" + this.name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        getWorldContainerMap().remove(this.name);
        new ConfigUtils().saveConfig();
    }

    public void createWorld(World.Environment environment) {
        Bukkit.createWorld(new WorldCreator(this.name).environment(environment));
    }
}
