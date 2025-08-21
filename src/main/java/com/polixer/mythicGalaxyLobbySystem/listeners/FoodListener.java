package com.polixer.mythicGalaxyLobbySystem.listeners;

import com.polixer.mythicGalaxyLobbySystem.util.Cfg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodListener implements Listener {
    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        if (!Cfg.noHunger()) return;
        e.setCancelled(true);
        e.setFoodLevel(20);
    }
}
