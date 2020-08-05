package de.mystarybreak.buildffa.listener;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class QuitListener implements Listener {

    private BuildFFA plugin;

    public QuitListener(BuildFFA plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player player = e.getPlayer();
        plugin.getStatsManager().savePlayer(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() > 0)
                    for (Player a : Bukkit.getOnlinePlayers())
                        plugin.getScoreboardManager().setBoard(a);
            }
        }.runTaskLater(plugin, 2);

    }

}
