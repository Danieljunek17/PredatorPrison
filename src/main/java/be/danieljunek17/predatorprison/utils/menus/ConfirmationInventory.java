package be.danieljunek17.predatorprison.utils.menus;

import be.danieljunek17.predatorprison.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

import static be.danieljunek17.predatorprison.utils.chat.__;

public class ConfirmationInventory extends StaticInventory {
    public ConfirmationInventory(JavaPlugin plugin, Player player, String title, Consumer<Void> confirm, Consumer<Void> deny) {
        super(plugin, title, new String[]{
                "         ",
                "   y n   ",
                "         "
        }, false);
        setFiller(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .displayname(__("&r"))
                .getItemStack());
        addElement('y', new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .displayname(__("&2Confirm"))
                .lore(__("&aConfirm your action."))
                .getItemStack(), click -> {
            confirm.accept(null);
            close(player);
            return true;
        });
        addElement('n', new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .displayname(__("&4Cancel"))
                .lore(__("&cCancel your action."))
                .getItemStack(), click -> {
            deny.accept(null);
            close(player);
            return true;
        });
        open(player);
    }
}
