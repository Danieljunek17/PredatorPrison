package be.danieljunek17.predatorprison.modules.privatemines.menus;

import be.danieljunek17.predatorprison.PredatorPrison;
import be.danieljunek17.predatorprison.objects.PrivateMine;
import be.danieljunek17.predatorprison.utils.ItemBuilder;
import be.danieljunek17.predatorprison.utils.menus.StaticInventory;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PrivateMineGUI extends StaticInventory {

    private static final String[] ROWS = {"yyfffffyy", "yfPfTfYfy", "yfffffffy", "yfRfUfSfy", "yyfffffyy"};

    public PrivateMineGUI(Player player, World world, PrivateMine privateMine) {
        super(PredatorPrison.getInstance(), "&e" + player.displayName() + "'s Mine", ROWS, true);

        addElement(new StaticGuiElement('y', new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).displayname(" ").getItemStack()));
        addElement(new StaticGuiElement('S', new ItemBuilder(Material.CHEST).displayname("&eMine Settings").lore("Change the settings", "of your mine").getItemStack(), click -> {

            return true;
        }));
        addElement(new StaticGuiElement('T', new ItemBuilder(Material.CHEST).displayname("&eTeleport to mine").getItemStack(), click -> {
            player.teleport(new Location(world, privateMine.getX(), 100D, privateMine.getZ()));
            return true;
        }));
    }
}
