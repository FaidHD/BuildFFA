package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StatsManager {

    private BuildFFA plugin;

    private HashMap<Player, PlayerStatistic> statistics;

    private MySQL stats;

    private int pointsForKill = 20;
    private int pointsForDeath = 10;

    public StatsManager(BuildFFA plugin) {
        this.plugin = plugin;
        this.statistics = new HashMap<>();
        stats = plugin.getData().getStats();
    }

    public void loadPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        ResultSet rs = stats.query("SELECT * FROM stats WHERE UUID = '" + uuid + "'");
        try {
            if (rs.next()) {
                stats.update("UPDATE stats SET PLAYERNAME = '" + player.getName() + "' WHERE UUID = '" + uuid + "'");
                int kills = rs.getInt("KILLS");
                int deaths = rs.getInt("DEATHS");
                int points = rs.getInt("POINTS");

                PlayerStatistic statistic = new PlayerStatistic(kills, deaths, points);
                statistics.put(player, statistic);
                return;
            }
            statistics.put(player, new PlayerStatistic(0, 0, 0));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void savePlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        ResultSet rs = stats.query("SELECT * FROM stats WHERE UUID = '" + uuid + "'");

        try {
            PlayerStatistic ps = statistics.get(player);
            if (rs.next()) {
                stats.update("UPDATE stats SET KILLS='" + ps.getKills() + "', DEATHS='" + ps.getDeaths() + "', POINTS='" + ps.getPoints() + "'  WHERE UUID = '" + uuid + "'");
                return;
            }
            stats.update("INSERT INTO stats(PLAYERNAME, UUID, KILLS, DEATHS, POINTS) VALUES ('" + player.getName() + "', '" + uuid + "', '" + ps.getKills() + "', '" + ps.getDeaths() + "', '" + ps.getPoints() + "')");
            statistics.put(player, new PlayerStatistic(0, 0, 0));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addKill(Player player) {
        PlayerStatistic ps = statistics.get(player);
        ps.addKills(1);
        ps.addPoints(pointsForKill);
    }

    public void addDeath(Player player) {
        PlayerStatistic ps = statistics.get(player);
        ps.addDeaths(1);
        ps.removePoints(pointsForDeath);
    }

    public int getKills(Player player) {
        return statistics.get(player).getKills();
    }

    public int getDeaths(Player player) {
        return statistics.get(player).getDeaths();
    }

    public int getPoints(Player player) {
        return statistics.get(player).getPoints();
    }

    public double getKD(Player player) {
        int deaths = getDeaths(player);
        int kills = getKills(player);

        if (deaths == 0) return kills;
        double d = (kills * 100) / deaths;
        double kd = Math.round(d);
        return kd / 100;
    }

}
