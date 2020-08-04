package de.mystarybreak.buildffa.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
    }

    public ItemBuilder(Material material, short i) {
        item = new ItemStack(material, 1, i);
        meta = item.getItemMeta();
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder(int id) {
        item = new ItemStack(id);
        meta = item.getItemMeta();
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder(int id, short subid) {
        item = new ItemStack(id, 1, subid);
        meta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        meta.spigot().setUnbreakable(true);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int lvl, boolean limited) {
        meta.addEnchant(ench, lvl, limited);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}

