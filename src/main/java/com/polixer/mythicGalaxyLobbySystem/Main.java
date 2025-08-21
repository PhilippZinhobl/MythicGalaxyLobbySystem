package com.polixer.mythicGalaxyLobbySystem;

import com.polixer.mythicGalaxyLobbySystem.commands.MgCommand;
import com.polixer.mythicGalaxyLobbySystem.gui.SettingsGui;
import com.polixer.mythicGalaxyLobbySystem.listeners.ExpListener;
import com.polixer.mythicGalaxyLobbySystem.listeners.FoodListener;
import com.polixer.mythicGalaxyLobbySystem.listeners.JoinListener;
import com.polixer.mythicGalaxyLobbySystem.util.Cfg;
import com.polixer.mythicGalaxyLobbySystem.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;

public class Main extends JavaPlugin {

    private static Main instance;
    private SettingsGui settingsGui;

    public static Main inst() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Cfg.load(this.getConfig());

        settingsGui = new SettingsGui();
        getCommand("mg").setExecutor(new MgCommand());
        getCommand("mg").setTabCompleter(new MgCommand());

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExpListener(), this);
        Bukkit.getPluginManager().registerEvents(settingsGui, this);

        long ticks = Cfg.updateIntervalMinutes() * 60L * 20L;
        if (ticks < 20L) ticks = 20L;
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                enforce(p);
            }
        }, 40L, ticks);

        getLogger().info("MythicGalaxyLobbySystem enabled.");
    }

    public static void enforce(Player p) {
        if (Cfg.lockLevelToYear()) {
            int year = LocalDate.now().getYear();
            if (p.getLevel() != year) p.setLevel(year);
        }
        if (Cfg.noHunger()) {
            p.setFoodLevel(20);
            p.setSaturation(20f);
            p.setExhaustion(0f);
        }
        double maxHp = Math.max(1, Cfg.hearts()) * 2.0;
        try {
            var attr = p.getAttribute(Attribute.MAX_HEALTH);
            if (attr != null && attr.getBaseValue() != maxHp) {
                attr.setBaseValue(maxHp);
            }
            if (p.getHealth() > maxHp) p.setHealth(maxHp);
        } catch (Exception ignored) {}
    }

    public SettingsGui settingsGui() { return settingsGui; }

    public void reloadAll() {
        reloadConfig();
        Cfg.load(getConfig());
        Bukkit.getOnlinePlayers().forEach(Main::enforce);
        getLogger().info("Konfiguration neu geladen.");
    }
}
