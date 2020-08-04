package de.mystarybreak.buildffa;

import de.mystarybreak.buildffa.utils.MySQL;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Data {

    @Getter private BuildFFA plugin;

    @Getter private String prefix = "§8┃ §aBuildFFA §8● §7";
    @Getter private String noperm = prefix + "Dazu hast du keine Rechte.";

    @Getter private MySQL stats;

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

    public void sendAllActionBar(String msg) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, msg);
        }
    }

    private void sendActionBar(Player player, String Nachricht) {
        String s = ChatColor.translateAlternateColorCodes('&', Nachricht);
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + s + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }

}
