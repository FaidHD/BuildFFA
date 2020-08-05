package de.mystarybreak.buildffa.utils.kits;

import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    ItemStack[] contents = new ItemStack[36];
    ItemStack[] armorContents = new ItemStack[4];
    String kitName = "";
    String scoreboardName = "";

    public ItemStack[] getContents() {
        return contents;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public String getKitName() {
        return kitName;
    }

    public String getScoreboardName() {
        return scoreboardName;
    }
}
