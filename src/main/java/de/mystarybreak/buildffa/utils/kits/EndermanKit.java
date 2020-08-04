package de.mystarybreak.buildffa.utils.kits;

import de.mystarybreak.buildffa.BuildFFA;
import de.mystarybreak.buildffa.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class EndermanKit extends Kit {

    private BuildFFA plugin;

    public EndermanKit(BuildFFA plugin) {
        this.plugin = plugin;
        kitName = "§7Kit §8• §aEnderman";
        scoreboardName = "Enderman";
        initInventory();
    }

    private void initInventory() {
        inventory.setItem(0, new ItemBuilder(Material.STONE_SWORD).setName(kitName).addEnchantment(Enchantment.DAMAGE_ALL, 1, false).setUnbreakable().build());
        inventory.setItem(1, new ItemBuilder(Material.ENDER_PEARL).setName(kitName).setAmount(5).build());
        inventory.setItem(7, new ItemBuilder(Material.SANDSTONE).setName(kitName).setAmount(64).build());
        inventory.setItem(8, new ItemBuilder(Material.SANDSTONE).setName(kitName).setAmount(64).build());

        inventory.setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build());
        inventory.setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build());
        inventory.setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build());
        inventory.setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build());
    }
}
