package be.danieljunek17.predatorprison.utils.menus;

import de.themoep.inventorygui.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

import static be.danieljunek17.predatorprison.utils.chat.__;

public class StaticInventory extends InventoryGui {
    protected StaticInventory(JavaPlugin plugin, String title, String[] rows, boolean closable) {
        super(plugin, __("&8" + title), rows);
        if (!closable) setCloseAction(close -> {
            close.getGui().show(close.getPlayer());
            return false;
        });
    }

    protected void open(Player player) {
        open(player, false);
    }

    protected void open(Player player, boolean force) {
        show(player, !force);
    }

    protected void close(Player player) {
        close(player, true);
    }

    protected void close(Player player, boolean cleanup) {
        Optional.ofNullable(getOpen(player)).ifPresent(gui -> gui.close(cleanup));
    }
}

