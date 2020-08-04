package de.mystarybreak.buildffa.commands;

import de.mystarybreak.buildffa.BuildFFA;
import de.mystarybreak.buildffa.utils.WorldContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class WorldManagerCommand implements CommandExecutor {

    private BuildFFA plugin;

    public WorldManagerCommand(BuildFFA plugin) {
        this.plugin = plugin;
        plugin.getCommand("worldmanager").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.getData().getPrefix() + "Du musst ein Spieler sein, um diesen Befehl nutzen zu können.");
            return false;
        }

        Player p = (Player) commandSender;
        if (p.hasPermission("knockit.admin")) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("import")) {
                    if (!WorldContainer.getWorldContainerMap().containsKey(args[1])) {
                        if (new File(System.getProperty("user.dir") + "/" + args[1]).exists()) {
                            WorldContainer wc = new WorldContainer(args[1]);
                            wc.loadWorld();
                            wc.setSpawnLocation(Bukkit.getWorld(args[1]).getSpawnLocation());
                            plugin.getConfigUtils().saveConfig();
                            p.sendMessage(plugin.getData().getPrefix() + "Du hast die Welt §a" + args[1] + " §7erfolgreich importiert.");
                        } else
                            p.sendMessage(plugin.getData().getPrefix() + "Die Welt existiert nicht.");
                    } else
                        p.sendMessage(plugin.getData().getPrefix() + "Die Welt §a" + args[1] + " §7existiert bereits.");
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (WorldContainer.getWorldContainerMap().containsKey(args[1])) {
                        Bukkit.getOnlinePlayers().forEach(a -> {
                            if (a.getWorld().getName().equals(args[1])) {
                                a.teleport(Bukkit.getWorld("world").getSpawnLocation());
                                a.sendMessage(plugin.getData().getPrefix() + "Du wurdest auf eine andere Welt verschoben, da die Welt gelöscht wurde.");
                            }
                        });
                        WorldContainer.getWorldContainerMap().get(args[1]).deleteWorld();
                        p.sendMessage(plugin.getData().getPrefix() + "Du hast die Welt §a" + args[1] + " §7erfolgreich gelöscht.");
                    }
                } else if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("teleport")) {
                    if (WorldContainer.getWorldContainerMap().containsKey(args[1])) {
                        p.teleport(WorldContainer.getWorldContainerMap().get(args[1]).getSpawnLocation());
                        p.sendMessage(plugin.getData().getPrefix() + "Du wurdest in die Welt §a" + args[1] + " §7teleportiert.");
                    } else
                        p.sendMessage(plugin.getData().getPrefix() + "Die Map §a" + args[1] + " §7existiert nicht.");
                } else
                    sendHelp(p);
            }else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("list")) {
                    p.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
                    p.sendMessage(plugin.getData().getPrefix());
                    int i = 1;
                    for(String name : WorldContainer.getWorldContainerMap().keySet()) {
                        p.sendMessage(plugin.getData().getPrefix() + "§a" +  i + " §8» §a" + name);
                        i++;
                    }
                    p.sendMessage(plugin.getData().getPrefix());
                    p.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
                }else
                    sendHelp(p);
            }else
                sendHelp(p);
        }else
            p.sendMessage(plugin.getData().getNoperm());

        return false;
    }

    private void sendHelp(Player p) {
        p.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/wm import <Welt> §8» §7Importiere eine Welt");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/wm delete <Welt> §8» §7Lösche eine Welt");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/wm tp <Welt> §8» §7Teleportiere dich in eine Welt");
        p.sendMessage(plugin.getData().getPrefix() + "§8- §a/wm list §8» §7Zeige alle importierten Maps an");
        p.sendMessage(plugin.getData().getPrefix() + "§8§l---------- §7« §aBuildFFA§7 » §8§l----------");
    }
}
