package de.mystarybreak.buildffa.listener;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
        plugin.getStatsManager().loadPlayer(player);
        player.getInventory().clear();
        plugin.getScoreboardManager().updateScoreboardForAll();
        if (plugin.getMapManager().getCurrentMap() != null)
            player.teleport(plugin.getMapManager().getCurrentMap().getSpawnPoint());
        if (!plugin.getMapManager().getMapPool().isEmpty()) {
            player.getInventory().setContents(plugin.getKitManager().getCurrentKit().getContents());
            player.getInventory().setArmorContents(plugin.getKitManager().getCurrentKit().getArmorContents());
        }
    }

}
