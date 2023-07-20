package be.danieljunek17.predatorprison.modules.privatemines;

import be.danieljunek17.predatorprison.PredatorPrison;
import be.danieljunek17.predatorprison.modules.privatemines.generators.PMineGenerator;
import be.danieljunek17.predatorprison.modules.privatemines.managers.PrivateMineManager;
import be.danieljunek17.predatorprison.modules.privatemines.menus.PrivateMineGUI;
import be.danieljunek17.predatorprison.objects.PrivateMine;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@CommandAlias("privatemines|privatemine|pmine|mine")
public class Commands extends BaseCommand {
    JavaPlugin plugin;
    PMineGenerator pMineGenerator;

    PrivateMineManager privateMineManager;
    World world;
    public Commands(JavaPlugin plugin, PMineGenerator pMineGenerator, PrivateMineManager privateMineManager, World world) {
        this.plugin = plugin;
        this.pMineGenerator = pMineGenerator;
        this.privateMineManager = privateMineManager;
        this.world = world;
    }

    @Subcommand("help")
    @CatchUnknown
    @HelpCommand
    public static void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Default
    public void privateMine(CommandSender sender) {
        Player player = (Player) sender;

        PrivateMine privateMine = privateMineManager.getPrivateMine(player.getUniqueId());
        if(privateMine == null) {
            pMineGenerator.generatePMine(PredatorPrison.getInstance(), privateMineManager, world);
            privateMine = privateMineManager.getPrivateMine(player.getUniqueId());
        }

        new PrivateMineGUI(player, world, privateMine);
    }

    @Subcommand("reset")
    @CommandPermission("predatorprison.pmine.reset")
    public void resetPrivateMine(CommandSender sender) {
        Player player = (Player) sender;

        PrivateMine privateMine = privateMineManager.getPrivateMine(player.getUniqueId());
        if(privateMine == null) {
            pMineGenerator.generatePMine(PredatorPrison.getInstance(), privateMineManager, world);
            privateMine = privateMineManager.getPrivateMine(player.getUniqueId());
        }

        //TODO: add all this into leveling system and configs
        Map<String, Integer> blocks = new HashMap<>();
        blocks.put("cobblestone", 80);
        blocks.put("stone", 20);

        privateMineManager.resetPrivateMine(world, privateMine, blocks);
    }

    @Subcommand("pregen")
    @CommandCompletion("@range:250")
    @CommandPermission("predatorprison.pmine.pregen")
    public void pregenPrivateMines(CommandSender sender, int amount) {
//        if(amount > 250) {
//            amount = 250;
//        }
        pMineGenerator.generateMultiplePMines(plugin, privateMineManager, world, amount);
    }
}
