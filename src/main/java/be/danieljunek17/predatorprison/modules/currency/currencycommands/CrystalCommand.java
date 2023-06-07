package be.danieljunek17.predatorprison.modules.currency.currencycommands;

import be.danieljunek17.predatorprison.managers.UserManager;
import be.danieljunek17.predatorprison.objects.User;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("crystals")
public class CrystalCommand extends BaseCommand {

    @Subcommand("help")
    @Description("open this menu")
    @CommandPermission("PredatorPrison.crystals.help")
    @CommandCompletion("@nothing")
    @CatchUnknown
    @HelpCommand
    public static void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Default
    public void seeCrystals(CommandSender sender) {
        Player player = (Player) sender;
        User user = UserManager.getUser(player.getUniqueId());

        player.sendMessage("You have " + user.getCrystals() + " crystals");
    }

    @Subcommand("give")
    @Description("give crystals to player")
    @CommandPermission("predatorprison.crystals.give")
    @CommandCompletion("@players @nothing")
    public void giveCrystals(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.addCrystals(amount);
        user.save();
        sender.sendMessage("You have added " + amount + " crystals to " + target.getPlayer().getName());
    }

    @Subcommand("remove")
    @Description("remove crystals from the player")
    @CommandPermission("predatorprison.crystals.remove")
    @CommandCompletion("@players @nothing")
    public void removeCrystals(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.removeCrystals(amount);
        user.save();
        sender.sendMessage("You have removed" + amount + " crystals from " + target.getPlayer().getUniqueId());
    }

    @Subcommand("set")
    @Description("set the player's crystals to a certain amount")
    @CommandPermission("predatorprison.crystals.set")
    @CommandCompletion("@players @nothing")
    public void setCrystals(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.setCrystals(amount);
        user.save();
        sender.sendMessage("You have set the crystals to" + amount + " for " + target.getPlayer().getUniqueId());
    }
}
