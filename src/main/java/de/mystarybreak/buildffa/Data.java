package de.mystarybreak.buildffa;

import de.mystarybreak.buildffa.utils.MySQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Data {

    private BuildFFA plugin;

    private String prefix = "§8┃ §aBuildFFA §8● §7";
    private String noperm = prefix + "Dazu hast du keine Rechte.";

    private MySQL stats;

    private File file;
    private FileConfiguration cfg;

    public Data(BuildFFA plugin) {
        this.plugin = plugin;
        setupMySQL();
    }

    private void setupMySQL() {
        file = new File("plugins/BuildFFA", "mysql.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()) {
            cfg.set("Hostname", "localhost");
            cfg.set("Port", "3306");
            cfg.set("Database", "database");
            cfg.set("Username", "username");
            cfg.set("Password", "password");
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        stats = new MySQL(cfg.getString("Hostname"), cfg.getString("Port"), cfg.getString("Database"), cfg.getString("Username") ,cfg.getString("Password"), plugin);
        stats.update("CREATE TABLE IF NOT EXISTS stats(PLAYERNAME VARCHAR(16), UUID VARCHAR(64), KILLS INT, DEATHS INT, POINTS INT)");
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNoperm() {
        return noperm;
    }

    public MySQL getStats() {
        return stats;
    }
}
