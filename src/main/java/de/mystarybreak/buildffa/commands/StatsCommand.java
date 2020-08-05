package de.mystarybreak.buildffa.commands;

import de.mystarybreak.buildffa.BuildFFA;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private BuildFFA plugin;

    public StatsCommand(BuildFFA plugin) {
        this.plugin = plugin;
        plugin.getCommand("stats").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.getData().getPrefix() + "Du musst ein Spieler sein, um diesen Befehl nutzen zu können.");
            return false;
        }

        Player player = (Player) commandSender;

        if(args.length == 0) {
            player.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
            player.sendMessage(plugin.getData().getPrefix() + "§8 » §7Deine Stats");
            player.sendMessage(plugin.getData().getPrefix() + "§8 » §7Points §8● §a" + plugin.getStatsManager().getPoints(player));
            player.sendMessage(plugin.getData().getPrefix() + "§8 » §7Kills §8● §a" + plugin.getStatsManager().getKills(player));
            player.sendMessage(plugin.getData().getPrefix() + "§8 » §7Deaths §8● §a" + plugin.getStatsManager().getDeaths(player));
            player.sendMessage(plugin.getData().getPrefix() + "§8 » §7K/D §8● §a" + plugin.getStatsManager().getKD(player));
            player.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
        }else if(args.length == 1) {
            String[] stats = plugin.getStatsManager().getOfflineStats(args[0]);
            if(stats != null) {
                player.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
                player.sendMessage(plugin.getData().getPrefix() + "§8 » §a" + args[0] + "'s §7Stats");
                player.sendMessage(plugin.getData().getPrefix() + "§8 » §7Points §8● §a" + stats[0]);
                player.sendMessage(plugin.getData().getPrefix() + "§8 » §7Kills §8● §a" + stats[1]);
                player.sendMessage(plugin.getData().getPrefix() + "§8 » §7Deaths §8● §a" + stats[2]);
                player.sendMessage(plugin.getData().getPrefix() + "§8 » §K/D §8● §a" + stats[3]);
                player.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
            }else
                player.sendMessage(plugin.getData().getPrefix() + "Der Spieler §a" + args[0] + " §7konnte nicht gefunden werden.");
        }else
            player.sendMessage(plugin.getData().getPrefix() + "Bitte benutze: /stats [Spieler]");
        return false;
    }
}
