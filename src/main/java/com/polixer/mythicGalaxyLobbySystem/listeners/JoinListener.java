package com.polixer.mythicGalaxyLobbySystem.listeners;

import com.polixer.mythicGalaxyLobbySystem.Main;
import com.polixer.mythicGalaxyLobbySystem.util.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.enforce(e.getPlayer());
    }
}
