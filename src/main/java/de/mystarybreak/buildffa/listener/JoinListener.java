package de.mystarybreak.buildffa.listener;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private BuildFFA plugin;

    public JoinListener(BuildFFA plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        plugin.getStatsManager().loadPlayer(player);
        if (plugin.getMapManager().getCurrentMap() != null)
            player.teleport(plugin.getMapManager().getCurrentMap().getSpawnPoint());
        for (Player a : Bukkit.getOnlinePlayers())
            plugin.getScoreboardManager().setBoard(a);
        if (!plugin.getMapManager().getMapPool().isEmpty())
            player.getInventory().setContents(plugin.getKitManager().getCurrentKit().getInventory().getContents());
    }

}
