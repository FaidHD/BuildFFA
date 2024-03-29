package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionbarManager {

    private BuildFFA plugin;

    public ActionbarManager(BuildFFA plugin) {
        this.plugin = plugin;
        startActionBarTimer();
    }

    private void startActionBarTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                updateActionBar();
            }
        }, 0, 10);
    }

    public void updateActionBar() {
        int kitSec = plugin.getKitManager().getSeconds();
        int kitMin = plugin.getKitManager().getMinutes();

        int mapSec = plugin.getMapManager().getSeconds();
        int mapMin = plugin.getMapManager().getMinutes();

        String secKit;
        if (kitSec < 10)
            secKit = "0" + kitSec;
        else
            secKit = "" + kitSec;

        String secMap;
        if (mapSec < 10)
            secMap = "0" + mapSec;
        else
            secMap = "" + mapSec;
        String actionbar = "§8§l✘  §2KitChange §7in §a" + kitMin + "§8:§a" + secKit + "  §8§l✘";
        if (plugin.getMapManager().getMapPool().size() > 1)
            actionbar = "§2KitChange §7in §a" + kitMin + "§8:§a" + secKit + "  §8§l✘  §2Mapchange §7in §a" + mapMin + "§8:§a" + secMap;
        sendAllActionBar(actionbar);
    }

    public void sendAllActionBar(String msg) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, msg);
        }
    }

    private void sendActionBar(Player player, String Nachricht) {
        String s = ChatColor.translateAlternateColorCodes('&', Nachricht);
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + s + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }
}
