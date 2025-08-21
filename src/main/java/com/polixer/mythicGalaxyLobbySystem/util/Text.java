package com.polixer.mythicGalaxyLobbySystem.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

public class Text {
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String pref(String msg) {
        return color(Cfg.prefix()) + color(msg);
    }

    public static void msg(CommandSender to, String raw) {
        if (to == null) return;
        to.spigot().sendMessage(new TextComponent(pref(raw)));
    }
}
