package de.mystarybreak.buildffa.listener;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private BuildFFA plugin;

    public DeathListener(BuildFFA plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (player.getKiller() != null) {
            plugin.getStatsManager().addKill(player.getKiller());
            player.sendMessage(plugin.getData().getPrefix() + "Du wurdest von §a" + player.getKiller().getName() + " §7getötet.");
            player.getKiller().sendMessage(plugin.getData().getPrefix() + "Du hast §a" + player.getName() + " §7getötet.");
            player.getKiller().sendMessage(plugin.getData().getPrefix() + "§a+ 20 Punkte");
        } else
            player.sendMessage(plugin.getData().getPrefix() + "Du bist gestorben.");
        player.sendMessage(plugin.getData().getPrefix() + "§c- 10 Punkte");
        plugin.getStatsManager().addDeath(player);
    }
}
