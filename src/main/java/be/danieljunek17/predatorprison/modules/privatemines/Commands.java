package be.danieljunek17.predatorprison.modules.privatemines;

import be.danieljunek17.predatorprison.managers.GridManager;
import be.danieljunek17.predatorprison.modules.privatemines.generators.PMineGenerator;
import be.danieljunek17.predatorprison.modules.privatemines.objects.Grid;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@CommandAlias("privatemines|privatemine|pmine|mine")
public class Commands extends BaseCommand {
    JavaPlugin plugin;
    PMineGenerator pMineGenerator;
    World world;
    public Commands(JavaPlugin plugin, PMineGenerator pMineGenerator, World world) {
        this.plugin = plugin;
        this.pMineGenerator = pMineGenerator;
        this.world = world;
    }

    @Subcommand("help")
    @CatchUnknown
    @HelpCommand
    public static void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("pregen")
    @CommandCompletion("@range:250)")
    @CommandPermission("predatorprison.mines.pregen")
    public void pregenPrivateMines(CommandSender sender, int amount) {
        if(amount > 250) {
            amount = 250;
        }
        for(int i = 0; i < amount; i++) {
            Grid grid = GridManager.getGrid();
            pMineGenerator.generatePMine(plugin, world, plugin.getDataFolder() + "/schematics/Private.schem", grid.getGridX(), grid.getGridZ());
            grid.getNewGridLocation();
            grid.save();
        }
    }
}
