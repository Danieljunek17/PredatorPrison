package be.danieljunek17.predatorprison.utils.menus;

import de.themoep.inventorygui.GuiElement;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.GuiPageElement;
import de.themoep.inventorygui.GuiPageElement.PageAction;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;
public class DynamicInventory extends StaticInventory {
    private final GuiElementGroup group;
    private boolean firstOpenDone = false;

    protected DynamicInventory(JavaPlugin plugin, String title, int itemRows, boolean pagination, boolean closable) {
        super(plugin, title, constructRowArray(itemRows, pagination), closable);
        this.group = new GuiElementGroup('g');

        if (pagination) {
            addElement(new GuiPageElement('f', new ItemStack(Material.ARROW), PageAction.FIRST, "Go to first page (current: %page%)"));
            addElement(new GuiPageElement('p', new ItemStack(Material.OAK_SIGN), PageAction.PREVIOUS, "Go to previous page (%prevpage%)"));
            addElement(new GuiPageElement('n', new ItemStack(Material.OAK_SIGN), PageAction.NEXT, "Go to next page (%nextpage%)"));
            addElement(new GuiPageElement('l', new ItemStack(Material.ARROW), PageAction.LAST, "Go to last page (%pages%)"));
        }
    }

    @Override
    protected void open(Player player, boolean force) {
        if (!firstOpenDone) {
            addElement(group);
            firstOpenDone = true;
        }
        super.open(player, force);
    }

    private static String[] constructRowArray(int rows, boolean pagination) {
        List<String> rowsList = new LinkedList<>();
        for (int i = 0; i < rows; i++) rowsList.add("g".repeat(9));
        if (pagination) rowsList.add("f  p ns l");
        return rowsList.toArray(String[]::new);
    }

    public void addItem(ItemStack item) {
        group.addElement(new StaticGuiElement('e', item));
    }

    public void addItem(ItemStack item, DynamicInventoryAction action) {
        group.addElement(new StaticGuiElement('e', item, click -> {
            action.onClick(click);
            return true;
        }));
    }

    public interface DynamicInventoryAction {
        void onClick(GuiElement.Click click);
    }
}

