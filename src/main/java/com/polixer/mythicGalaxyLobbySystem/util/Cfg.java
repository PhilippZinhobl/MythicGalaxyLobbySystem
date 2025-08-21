package com.polixer.mythicGalaxyLobbySystem.util;

import org.bukkit.configuration.file.FileConfiguration;

public class Cfg {
    private static String prefix;
    private static int hearts;
    private static boolean noHunger;
    private static boolean lockLevelToYear;
    private static boolean allowXpPickup;
    private static int updateIntervalMinutes;

    public static void load(FileConfiguration c) {
        prefix = c.getString("prefix", "&4&lMythic &5&lGalaxy &8>>&d ");
        hearts = Math.max(1, c.getInt("hearts", 3));
        noHunger = c.getBoolean("no-hunger", true);
        lockLevelToYear = c.getBoolean("lock-level-to-year", true);
        allowXpPickup = c.getBoolean("allow-xp-pickup", false);
        updateIntervalMinutes = Math.max(1, c.getInt("update-interval-minutes", 10));
    }

    public static String prefix() { return prefix; }
    public static int hearts() { return hearts; }
    public static boolean noHunger() { return noHunger; }
    public static boolean lockLevelToYear() { return lockLevelToYear; }
    public static boolean allowXpPickup() { return allowXpPickup; }
    public static int updateIntervalMinutes() { return updateIntervalMinutes; }

    public static void setHearts(int v) { hearts = v; }
    public static void setNoHunger(boolean v) { noHunger = v; }
    public static void setLockLevelToYear(boolean v) { lockLevelToYear = v; }
}
