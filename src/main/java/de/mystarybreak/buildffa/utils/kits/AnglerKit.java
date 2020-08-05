package de.mystarybreak.buildffa.utils.kits;

import de.mystarybreak.buildffa.BuildFFA;
import de.mystarybreak.buildffa.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class AnglerKit extends Kit {

    private BuildFFA plugin;

    public AnglerKit(BuildFFA plugin) {
        this.plugin = plugin;
        kitName = "§7Kit §8• §aAngler";
        scoreboardName = "Angler";
        initInventory();
    }

    private void initInventory() {
        contents[0] = new ItemBuilder(Material.IRON_SWORD).setName(kitName).setUnbreakable().build();
        contents[1] = new ItemBuilder(Material.FISHING_ROD).setName(kitName).setUnbreakable().build();
        contents[7] = new ItemBuilder(Material.SANDSTONE).setName(kitName).setAmount(64).build();
        contents[8] = new ItemBuilder(Material.SANDSTONE).setName(kitName).setAmount(64).build();

        armorContents[3] = new ItemBuilder(Material.CHAINMAIL_HELMET).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build();
        armorContents[2] = new ItemBuilder(Material.IRON_CHESTPLATE).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build();
        armorContents[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build();
        armorContents[0] = new ItemBuilder(Material.CHAINMAIL_BOOTS).setName(kitName).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).setUnbreakable().build();
    }

}
