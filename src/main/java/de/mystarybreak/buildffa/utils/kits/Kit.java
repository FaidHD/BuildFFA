package de.mystarybreak.buildffa.utils.kits;

import org.bukkit.inventory.PlayerInventory;

public abstract class Kit {

    PlayerInventory inventory;
    String kitName;
    String scoreboardName;

    public PlayerInventory getInventory() {
        return inventory;
    }

    public String getKitName() {
        return kitName;
    }

    public String getScoreboardName() {
        return scoreboardName;
    }
}
