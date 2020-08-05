package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ScoreboardManager {

    private BuildFFA plugin;

    public ScoreboardManager(BuildFFA plugin) {
        this.plugin = plugin;
    }

    public void setBoard(Player player) {
        Scoreboard sb = new Scoreboard();
        ScoreboardObjective obj = sb.registerObjective("dummy", IScoreboardCriteria.b);
        obj.setDisplayName("§8•● §aBuildFFA");
        PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

        ScoreboardScore a0 = new ScoreboardScore(sb, obj, "§r§r§r§r§r§r§r");
        ScoreboardScore a1 = new ScoreboardScore(sb, obj, "§8» §7Online");
        ScoreboardScore a2 = new ScoreboardScore(sb, obj, "§7•● §a" + Bukkit.getOnlinePlayers().size() + "§9");
        ScoreboardScore a3 = new ScoreboardScore(sb, obj, "§8§7§5§7");
        ScoreboardScore a4 = new ScoreboardScore(sb, obj, "§8» §7Map");
        ScoreboardScore a5 = new ScoreboardScore(sb, obj, "§7•● §a" + plugin.getMapManager().getCurrentMap().getName());
        ScoreboardScore a6 = new ScoreboardScore(sb, obj, "§8§7§5§6");
        ScoreboardScore a7 = new ScoreboardScore(sb, obj, "§8» §7Kit");
        ScoreboardScore a8 = new ScoreboardScore(sb, obj, "§1§6§7•● §a" + plugin.getKitManager().getCurrentKit().getScoreboardName());
        ScoreboardScore a9 = new ScoreboardScore(sb, obj, "§8§7§6");
        ScoreboardScore a10 = new ScoreboardScore(sb, obj, "§8» §7K/D");
        ScoreboardScore a11 = new ScoreboardScore(sb, obj, "§5§3§7•● §a" + plugin.getStatsManager().getKD(player));

        a0.setScore(12);
        a1.setScore(11);
        a2.setScore(10);
        a3.setScore(9);
        a4.setScore(8);
        a5.setScore(7);
        a6.setScore(6);
        a7.setScore(5);
        a8.setScore(4);
        a9.setScore(3);
        a10.setScore(2);
        a11.setScore(1);

        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardScore pa0 = new PacketPlayOutScoreboardScore(a0);
        PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(a1);
        PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(a2);
        PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(a3);
        PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(a4);
        PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(a5);
        PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(a6);
        PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(a7);
        PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(a8);
        PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(a9);
        PacketPlayOutScoreboardScore pa10 = new PacketPlayOutScoreboardScore(a10);
        PacketPlayOutScoreboardScore pa11 = new PacketPlayOutScoreboardScore(a11);

        sendPacket(player, removePacket);
        sendPacket(player, createPacket);
        sendPacket(player, display);

        sendPacket(player, pa0);
        sendPacket(player, pa1);
        sendPacket(player, pa2);
        sendPacket(player, pa3);
        sendPacket(player, pa4);
        sendPacket(player, pa5);
        sendPacket(player, pa6);
        sendPacket(player, pa7);
        sendPacket(player, pa8);
        sendPacket(player, pa9);
        sendPacket(player, pa10);
        sendPacket(player, pa11);
    }

    @SuppressWarnings("rawtypes")
    private void sendPacket(Player p, Packet packet) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
