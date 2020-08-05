package de.mystarybreak.buildffa.commands;

import de.mystarybreak.buildffa.BuildFFA;
import de.mystarybreak.buildffa.utils.LocationManager;
import de.mystarybreak.buildffa.utils.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MapCommand implements CommandExecutor {

    private BuildFFA plugin;

    private HashMap<Player, Map> maps;

    public MapCommand(BuildFFA plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("map").setExecutor(this);
        maps = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.getData().getPrefix() + "Du musst ein Spieler sein, um diesen Befehl nutzen zu können.");
            return false;
        }

        Player player = (Player) commandSender;
        if (player.hasPermission("buildffa.admin")) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (!plugin.getMapManager().mapExists(args[1])) {
                        if (!maps.containsKey(player)) {
                            maps.put(player, new Map(args[1]));
                            player.sendMessage(plugin.getData().getPrefix() + "Du hast erfolgreich die Map §a" + args[1] + " §7erstellt.");
                            player.sendMessage(plugin.getData().getPrefix() + "Setze jetzt den Spawn mit §a/map setspawn§7.");
                        } else
                            player.sendMessage(plugin.getData().getPrefix() + "Du hast bereits eine Map erstellt. Schließe das Setup erst ab, um eine neue zu erstellen.");
                    } else
                        player.sendMessage(plugin.getData().getPrefix() + "Eine Map mit gleichem Namen existiert bereits.");
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (plugin.getMapManager().mapExists(args[1])) {
                        plugin.getMapManager().removeMap(args[1]);
                        player.sendMessage(plugin.getData().getPrefix() + "Du hast die Map §a" + args[1] + " §7erfolgreich gelöscht.");
                        player.sendMessage(plugin.getData().getPrefix() + "Damit die Änderung wirksam wird, führe §a/map reload §7aus.");
                    }
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("setspawn")) {
                    if (maps.containsKey(player)) {
                        maps.get(player).setSpawnPoint(new LocationManager().getLocationInMiddle(player.getLocation()));
                        player.sendMessage(plugin.getData().getPrefix() + "Du hast erfolgreich den Spawn gesetzt.");
                        player.sendMessage(plugin.getData().getPrefix() + "Setze nun die Todeshöhe mit §a/map setdeath");
                    } else
                        player.sendMessage(plugin.getData().getPrefix() + "Bitte erstelle erst eine Map mit §a/map create <Name>");
                } else if (args[0].equalsIgnoreCase("setdeath")) {
                    if (maps.containsKey(player)) {
                        maps.get(player).setDeathHeight(player.getLocation().getBlockY());
                        player.sendMessage(plugin.getData().getPrefix() + "Du hast erfolgreich die Todeshöhe gesetzt.");
                        player.sendMessage(plugin.getData().getPrefix() + "Setze nun die PvP-Höhe mit §a/map setpvp");
                    } else
                        player.sendMessage(plugin.getData().getPrefix() + "Bitte erstelle erst eine Map mit §a/map create <Name>");
                } else if (args[0].equalsIgnoreCase("setpvp")) {
                    if (maps.containsKey(player)) {
                        maps.get(player).setPvpHeight(player.getLocation().getBlockY());
                        player.sendMessage(plugin.getData().getPrefix() + "Du hast erfolgreich die PvP-Höhe gesetzt.");
                        player.sendMessage(plugin.getData().getPrefix() + "Speichere nun die Map mit §a/map save");
                    } else
                        player.sendMessage(plugin.getData().getPrefix() + "Bitte erstelle erst eine Map mit §a/map create <Name>");
                } else if (args[0].equalsIgnoreCase("save")) {
                    if (maps.containsKey(player)) {
                        plugin.getMapManager().addMap(maps.get(player));
                        player.sendMessage(plugin.getData().getPrefix() + "Du hast die Map erfolgreich gespeichert.");
                        player.sendMessage(plugin.getData().getPrefix() + "Damit die Änderung wirksam wird, führe §a/map reload §7aus.");
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
                    for (Map map : plugin.getMapManager().getMapPool())
                        player.sendMessage(plugin.getData().getPrefix() + "§8- §a" + map.getName());
                    player.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
                } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                    plugin.getMapManager().loadMaps();
                    plugin.getKitManager().startKitChange();
                    player.sendMessage(plugin.getData().getPrefix() + "Du hast die Maps erfolgreich neu geladen.");
                    player.sendMessage(plugin.getData().getPrefix() + "Es wurden §a" + plugin.getMapManager().getMapPool().size() + " §7Maps geladen.");
                } else
                    sendHelp(player);
            } else
                sendHelp(player);
        } else
            player.sendMessage(plugin.getData().getNoperm());
        return false;
    }

    public void sendHelp(Player p) {
        p.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map create <Name> §8» §7Füge eine neue Map hinzu");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map setSpawn §8» §7Setze den Spawn");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map setDeath §8» §7Setze die Todeshöhe");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map setPvP §8» §7Setze die PvP-Höhe");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map save §8» §7Speichere die Map");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map list §8» §7Zeige alle aktiven Maps");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map reload §8» §7Lade alle Maps aus der Config neu.");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/map delete <Map> §8» §7Lösche eine Map");
        p.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
    }
}
