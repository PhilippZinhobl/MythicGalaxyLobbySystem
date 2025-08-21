package com.polixer.mythicGalaxyLobbySystem.listeners;

import com.polixer.mythicGalaxyLobbySystem.util.Cfg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.time.LocalDate;

public class ExpListener implements Listener {
    @EventHandler
    public void onExp(PlayerExpChangeEvent e) {
        if (!Cfg.lockLevelToYear()) return;
        if (!Cfg.allowXpPickup()) {
            e.setAmount(0);
        }
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent e) {
        if (!Cfg.lockLevelToYear()) return;
        int year = LocalDate.now().getYear();
        if (e.getPlayer().getLevel() != year) {
            e.getPlayer().setLevel(year);
        }
    }
}
