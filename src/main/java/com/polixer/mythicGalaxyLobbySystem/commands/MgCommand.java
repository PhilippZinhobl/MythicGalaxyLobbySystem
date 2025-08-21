package com.polixer.mythicGalaxyLobbySystem.commands;

import com.polixer.mythicGalaxyLobbySystem.Main;
import com.polixer.mythicGalaxyLobbySystem.util.Cfg;
import com.polixer.mythicGalaxyLobbySystem.util.Text;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MgCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) { Text.msg(sender, "Nur im Spiel nutzbar."); return true; }
            Player p = (Player) sender;
            if (!p.hasPermission("mythicgalaxy.use")) { Text.msg(p, "Keine Berechtigung."); return true; }
            Main.inst().settingsGui().open(p);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("mythicgalaxy.admin")) { Text.msg(sender, "Keine Berechtigung."); return true; }
            Main.inst().reloadAll();
            return true;
        }
        if (args[0].equalsIgnoreCase("prefix")) {
            if (!sender.hasPermission("mythicgalaxy.admin")) { Text.msg(sender, "Keine Berechtigung."); return true; }
            if (args.length < 2) { Text.msg(sender, "Nutze: /mg prefix <Text>"); return true; }
            String text = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
            Main.inst().getConfig().set("prefix", text);
            Main.inst().saveConfig();
            Cfg.load(Main.inst().getConfig());
            Text.msg(sender, "Prefix aktualisiert auf: " + Text.color(text));
            return true;
        }
        Text.msg(sender, "Unbekannter Befehl. Nutze /mg");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("mythicgalaxy.admin")) { list.add("reload"); list.add("prefix"); }
        }
        return list;
    }
}
