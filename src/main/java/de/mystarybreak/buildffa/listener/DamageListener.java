package de.mystarybreak.buildffa.listener;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private BuildFFA plugin;

    public DamageListener(BuildFFA plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && e.getCause() != EntityDamageEvent.DamageCause.MAGIC)
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) return;
        if(!(e.getEntity() instanceof Player)) return;
        if(plugin.getMapManager().getCurrentMap() == null) return;

        Player player = (Player) e.getEntity();
        if(player.getLocation().getBlockY() >= plugin.getMapManager().getCurrentMap().getPvpHeight())
            e.setCancelled(true);
    }
}
