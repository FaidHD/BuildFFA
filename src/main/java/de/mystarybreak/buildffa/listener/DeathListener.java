package de.mystarybreak.buildffa.listener;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;

public class DeathListener implements Listener {

    private BuildFFA plugin;
    private ArrayList<Player> deathPlayers;

    public DeathListener(BuildFFA plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
        deathPlayers = new ArrayList<>();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        e.setDeathMessage(null);
        e.setKeepInventory(true);
        e.setDroppedExp(0);
        if (player.getKiller() != null) {
            plugin.getStatsManager().addKill(player.getKiller());
            player.sendMessage(plugin.getData().getPrefix() + "Du wurdest von §a" + player.getKiller().getName() + " §7getötet.");
            player.getKiller().sendMessage(plugin.getData().getPrefix() + "Du hast §a" + player.getName() + " §7getötet.");
            player.getKiller().sendMessage(plugin.getData().getPrefix() + "§a+ 20 Punkte");
        } else
            player.sendMessage(plugin.getData().getPrefix() + "Du bist gestorben.");
        player.sendMessage(plugin.getData().getPrefix() + "§c- 10 Punkte");
        plugin.getStatsManager().addDeath(player);
        player.spigot().respawn();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if(plugin.getMapManager().getCurrentMap() != null)
            e.setRespawnLocation(plugin.getMapManager().getCurrentMap().getSpawnPoint());
        if(deathPlayers.contains(player))
            deathPlayers.remove(player);
        player.getInventory().clear();
        if (!plugin.getMapManager().getMapPool().isEmpty()) {
            player.getInventory().setContents(plugin.getKitManager().getCurrentKit().getContents());
            player.getInventory().setArmorContents(plugin.getKitManager().getCurrentKit().getArmorContents());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if(plugin.getMapManager().getCurrentMap() == null) return;
        if (!player.getWorld().equals(plugin.getMapManager().getCurrentMap().getSpawnPoint().getWorld())) return;
        if(deathPlayers.contains(player)) return;
        if(player.getLocation().getBlockY() < plugin.getMapManager().getCurrentMap().getDeathHeight()) {
            deathPlayers.add(player);
            player.setHealth(0);
        }
    }
}
