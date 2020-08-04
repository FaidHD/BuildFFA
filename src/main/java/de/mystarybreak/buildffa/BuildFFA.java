package de.mystarybreak.buildffa;

import de.mystarybreak.buildffa.commands.MapCommand;
import de.mystarybreak.buildffa.commands.WorldManagerCommand;
import de.mystarybreak.buildffa.listener.*;
import de.mystarybreak.buildffa.utils.*;
import de.mystarybreak.buildffa.utils.kits.KitManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BuildFFA extends JavaPlugin {

    @Getter private Data data;
    @Getter private StatsManager statsManager;
    @Getter private MapManager mapManager;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private ConfigUtils configUtils;
    @Getter private KitManager kitManager;
    @Getter private ActionbarManager actionbarManager;

    @Override
    public void onEnable() {
        data = new Data(this);
        configUtils = new ConfigUtils();
        statsManager = new StatsManager(this);
        mapManager = new MapManager(this);
        kitManager = new KitManager(this);
        scoreboardManager = new ScoreboardManager(this);
        actionbarManager = new ActionbarManager(this);
        initListeners();
        initCommands();
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
        new WorldManagerCommand(this);
    }
}