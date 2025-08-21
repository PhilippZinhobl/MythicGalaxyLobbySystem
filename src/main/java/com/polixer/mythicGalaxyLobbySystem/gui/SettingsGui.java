package com.polixer.mythicGalaxyLobbySystem.gui;

import com.polixer.mythicGalaxyLobbySystem.Main;
import com.polixer.mythicGalaxyLobbySystem.util.Cfg;
import com.polixer.mythicGalaxyLobbySystem.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SettingsGui implements Listener {

    private Inventory build() {
        String title = Text.color(Main.inst().getConfig().getString("gui.title", "&dSettings"));
        Inventory inv = Bukkit.createInventory(null, 27, title);

        inv.setItem(Main.inst().getConfig().getInt("gui.items.hearts.slot", 12),
                item("HEART_OF_THE_SEA", Text.color("&dHerzen: &f" + Cfg.hearts()),
                        List.of(Text.color("&7Linksklick: +1"), Text.color("&7Rechtsklick: -1"))));

        inv.setItem(Main.inst().getConfig().getInt("gui.items.hunger.slot", 13),
                item("BREAD", Text.color("&dHunger aus: &f" + Cfg.noHunger()),
                        List.of(Text.color("&7Klick: Toggle"))));

        inv.setItem(Main.inst().getConfig().getInt("gui.items.yearlevel.slot", 14),
                item("EXPERIENCE_BOTTLE", Text.color("&dLevel = Jahr: &f" + Cfg.lockLevelToYear()),
                        List.of(Text.color("&7Klick: Toggle"))));

        inv.setItem(Main.inst().getConfig().getInt("gui.items.save.slot", 22),
                item("EMERALD_BLOCK", Text.color("&aSpeichern"),
                        List.of(Text.color("&7Übernimmt in config.yml"))));

        return inv;
    }

    private ItemStack item(String matName, String name, List<String> lore) {
        Material mat = Material.matchMaterial(matName.toUpperCase());
        if (mat == null) mat = Material.PAPER;
        ItemStack is = new ItemStack(mat);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        is.setItemMeta(im);
        return is;
    }

    public void open(Player p) { p.openInventory(build()); }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        String title = Text.color(Main.inst().getConfig().getString("gui.title", "&dSettings"));
        if (!e.getView().getTitle().equals(title)) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;

        int slotHearts = 12;
        int slotHunger = 13;
        int slotYear   = 14;
        int slotSave   = 22;

        if (e.getSlot() == slotHearts) {
            int delta = e.isLeftClick() ? +1 : (e.isRightClick() ? -1 : 0);
            if (delta != 0) {
                Cfg.setHearts(Math.max(1, Cfg.hearts() + delta));
                Main.enforce((Player) e.getWhoClicked());
                open((Player) e.getWhoClicked());
            }
            return;
        }
        if (e.getSlot() == slotHunger) {
            Cfg.setNoHunger(!Cfg.noHunger());
            Main.enforce((Player) e.getWhoClicked());
            open((Player) e.getWhoClicked());
            return;
        }
        if (e.getSlot() == slotYear) {
            Cfg.setLockLevelToYear(!Cfg.lockLevelToYear());
            Main.enforce((Player) e.getWhoClicked());
            open((Player) e.getWhoClicked());
            return;
        }
        if (e.getSlot() == slotSave) {
            var cfg = Main.inst().getConfig();
            cfg.set("hearts", Cfg.hearts());
            cfg.set("no-hunger", Cfg.noHunger());
            cfg.set("lock-level-to-year", Cfg.lockLevelToYear());
            Main.inst().saveConfig();
            ((Player) e.getWhoClicked()).sendMessage(Text.pref("Gespeichert in config.yml"));
            e.getWhoClicked().closeInventory();
        }
    }
}
