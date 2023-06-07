package be.danieljunek17.predatorprison.modules.currency;

import be.danieljunek17.predatorprison.modules.Module;
import be.danieljunek17.predatorprison.modules.currency.currencycommands.CrystalCommand;
import be.danieljunek17.predatorprison.modules.currency.currencycommands.GemCommand;
import be.danieljunek17.predatorprison.modules.currency.currencycommands.MoneyCommand;
import be.danieljunek17.predatorprison.modules.currency.currencycommands.TokenCommand;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Currency implements Module {
    BaseCommand crystalCommand;
    BaseCommand gemCommand;
    BaseCommand moneyCommand;
    BaseCommand tokenCommand;

    @Override
    public void enable(JavaPlugin plugin, PaperCommandManager manager) {
        crystalCommand = new CrystalCommand();
        gemCommand = new GemCommand();
        moneyCommand = new MoneyCommand();
        tokenCommand = new TokenCommand();

        manager.registerCommand(crystalCommand);
        manager.registerCommand(gemCommand);
        manager.registerCommand(moneyCommand);
        manager.registerCommand(tokenCommand);
    }
    @Override
    public void disable(JavaPlugin plugin, PaperCommandManager manager) {
        manager.unregisterCommand(crystalCommand);
        manager.unregisterCommand(gemCommand);
        manager.unregisterCommand(moneyCommand);
        manager.unregisterCommand(tokenCommand);
    }
}
