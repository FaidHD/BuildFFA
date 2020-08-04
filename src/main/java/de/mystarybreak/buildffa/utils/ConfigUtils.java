package de.mystarybreak.buildffa.utils;

import com.google.common.collect.Lists;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigUtils {

    public ConfigUtils() {
        loadConfig();
    }

    final File file = new File("plugins/BuildFFA", "worlds.yml");
    final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);

    public void loadConfig() {
        for (String string : this.yamlConfiguration.getStringList("Worlds")) {
            WorldContainer worldContainer = new WorldContainer(string);
            if (this.yamlConfiguration.get(string + ".Location") != null) {
                Bukkit.createWorld(new WorldCreator(string));

                worldContainer.setSpawnLocation(deserializeLocation(this.yamlConfiguration.getString(string + ".Location")));
                worldContainer.loadWorld();
            }
        }
        for (World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.PEACEFUL);
            if (!WorldContainer.getWorldContainerMap().containsKey(world.getName())) {
                WorldContainer.getWorldContainerMap().put(world.getName(), new WorldContainer(world.getName()));
            }
        }
    }

    public void saveConfig() {
        List<String> worlds = Lists.newCopyOnWriteArrayList();
        for (String containerName : WorldContainer.getWorldContainerMap().keySet()) {
            WorldContainer worldContainer = WorldContainer.getWorldContainerMap().get(containerName);

            worlds.add(containerName);
            if (worldContainer.getSpawnLocation() != null) {
                this.yamlConfiguration.set(containerName + ".Location", serializeLocation(worldContainer.getSpawnLocation()));
            }
        }
        this.yamlConfiguration.set("Worlds", worlds);
        try {
            this.yamlConfiguration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serializeLocation(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    public Location deserializeLocation(String serializedLocation) {
        String[] data = serializedLocation.split(":");
        Location location = new Location(Bukkit.getWorld(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
        location.setYaw(Float.parseFloat(data[4]));
        location.setPitch(Float.parseFloat(data[5]));
        return location;
    }
}
