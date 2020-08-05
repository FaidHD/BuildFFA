package de.mystarybreak.buildffa;

import de.mystarybreak.buildffa.commands.MapCommand;
import de.mystarybreak.buildffa.commands.StatsCommand;
import de.mystarybreak.buildffa.listener.*;
import de.mystarybreak.buildffa.utils.*;
import de.mystarybreak.buildffa.utils.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BuildFFA extends JavaPlugin {

    private Data data;
    private StatsManager statsManager;
    private MapManager mapManager;
    private ScoreboardManager scoreboardManager;
    private KitManager kitManager;
    private ActionbarManager actionbarManager;

    @Override
    public void onEnable() {
        data = new Data(this);
        statsManager = new StatsManager(this);
        mapManager = new MapManager(this);
        kitManager = new KitManager(this);
        scoreboardManager = new ScoreboardManager(this);
        actionbarManager = new ActionbarManager(this);
        initListeners();
        initCommands();
        Bukkit.getConsoleSender().sendMessage("Das Plugin startet");
    }

    @Override
    public void onDisable() {
        getData().getStats().close();
    }

    private void initListeners() {
        new JoinListener(this);
        new BlockListener(this);
        new DamageListener(this);
        new DeathListener(this);
        new QuitListener(this);
    }

    private void initCommands() {
        new MapCommand(this);
        new StatsCommand(this);
    }

    public Data getData() {
        return data;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}