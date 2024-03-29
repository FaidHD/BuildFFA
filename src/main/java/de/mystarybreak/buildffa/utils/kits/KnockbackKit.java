package de.mystarybreak.buildffa.utils.kits;

import de.mystarybreak.buildffa.BuildFFA;
import de.mystarybreak.buildffa.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class KnockbackKit extends Kit {

    private BuildFFA plugin;

    public KnockbackKit(BuildFFA plugin) {
        this.plugin = plugin;
        scoreboardName = "Knüppel";
        kitName = "§7Kit §8• §aKnüppel";
        initInventory();
    }

    private void initInventory() {
        contents[0] = new ItemBuilder(Material.STICK).setName(kitName).addEnchantment(Enchantment.KNOCKBACK, 2, false).build();
        contents[1] = new ItemBuilder(Material.SANDSTONE).setName(kitName).setAmount(64).build();
        contents[2] = new ItemBuilder(Material.SANDSTONE).setName(kitName).setAmount(64).build();
    }

}
