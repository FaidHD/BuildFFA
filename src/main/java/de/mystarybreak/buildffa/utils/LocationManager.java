package de.mystarybreak.buildffa.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationManager {

    public Location getLocationInMiddle(Location location) {
        double x = location.getX() < 0 ? location.getX() - 0.5 : location.getX() + 0.5;
        double z = location.getZ() < 0 ? location.getZ() - 0.5 : location.getZ() + 0.5;
        return new Location(location.getWorld(), x, location.getY(), z, location.getYaw(), location.getPitch());
    }

    public String getLocAsString(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
    }

    public Location getStringAsLoc(String string) {
        String[] parts = string.split(":");
        return new Location(Bukkit.getWorld(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[5]));
    }

}
