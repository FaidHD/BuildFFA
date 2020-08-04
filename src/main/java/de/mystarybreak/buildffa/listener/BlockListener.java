package de.mystarybreak.buildffa.listener;

import de.mystarybreak.buildffa.BuildFFA;
import de.mystarybreak.buildffa.utils.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private BuildFFA plugin;

    public BlockListener(BuildFFA plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        MapManager mapManager = plugin.getMapManager();
        if(mapManager.getCurrentMap() == null) return;
        if(event.getBlock().getLocation().getY() >= mapManager.getCurrentMap().getPvpHeight()) {
            event.setCancelled(true);
            return;
        }
        mapManager.addPlacedBlock(event.getBlock());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

}
