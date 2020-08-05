package de.mystarybreak.buildffa.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationManager {

    public Location getLocationInMiddle(Location location) {
        double x = location.getBlockX() < 0 ? location.getBlockX() - 0.5 : location.getBlockX() + 0.5;
        double z = location.getBlockZ() < 0 ? location.getBlockZ() - 0.5 : location.getBlockZ() + 0.5;
        return new Location(location.getWorld(), x < 0 ? x + 1 : x, location.getY(), z < 0 ? z + 1 : z, location.getYaw(), location.getPitch());
    }

    public String getLocAsString(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public Location getStringAsLoc(String string) {
        String[] parts = string.split(":");
        return new Location(Bukkit.getWorld(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[5]));
    }

}
