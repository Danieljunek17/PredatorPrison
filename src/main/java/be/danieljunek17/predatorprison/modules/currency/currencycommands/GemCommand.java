package be.danieljunek17.predatorprison.modules.currency.currencycommands;

import be.danieljunek17.predatorprison.managers.UserManager;
import be.danieljunek17.predatorprison.objects.User;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("gems")
public class GemCommand extends BaseCommand {

    @Subcommand("help")
    @Description("open this menu")
    @CommandPermission("PredatorPrison.gems.help")
    @CommandCompletion("@nothing")
    @CatchUnknown
    @HelpCommand
    public static void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Default
    public void seeGems(CommandSender sender) {
        Player player = (Player) sender;
        User user = UserManager.getUser(player.getUniqueId());

        player.sendMessage("You have " + user.getGems() + " gems");
    }

    @Subcommand("give")
    @Description("give gems to player")
    @CommandPermission("predatorprison.gems.give")
    @CommandCompletion("@players @nothing")
    public void giveGems(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.addGems(amount);
        user.save();
        sender.sendMessage("You have added " + amount + " gems to " + target.getPlayer().getName());
    }

    @Subcommand("remove")
    @Description("remove gems from the player")
    @CommandPermission("predatorprison.gems.remove")
    @CommandCompletion("@players @nothing")
    public void removeGems(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.removeGems(amount);
        user.save();
        sender.sendMessage("You have removed " + amount + " gems from " + target.getPlayer().getName());
    }

    @Subcommand("set")
    @Description("set the player's gems to a certain amount")
    @CommandPermission("predatorprison.gems.set")
    @CommandCompletion("@players @nothing")
    public void setGems(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.setGems(amount);
        user.save();
        sender.sendMessage("You have set the gems to " + amount + " for " + target.getPlayer().getName());
    }
}
