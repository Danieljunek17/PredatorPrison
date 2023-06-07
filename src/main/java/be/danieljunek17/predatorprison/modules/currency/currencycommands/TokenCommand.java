package be.danieljunek17.predatorprison.modules.currency.currencycommands;

import be.danieljunek17.predatorprison.managers.UserManager;
import be.danieljunek17.predatorprison.objects.User;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tokens")
public class TokenCommand extends BaseCommand {

    @Subcommand("help")
    @Description("open this menu")
    @CommandPermission("predatorprison.tokens.help")
    @CommandCompletion("@nothing")
    @CatchUnknown
    @HelpCommand
    public static void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Default
    public void seeTokens(CommandSender sender) {
        Player player = (Player) sender;
        User user = UserManager.getUser(player.getUniqueId());

        player.sendMessage("You have " + user.getTokens() + " tokens");
    }

    @Subcommand("give")
    @Description("give tokens to player")
    @CommandPermission("predatorprison.tokens.give")
    @CommandCompletion("@players @nothing")
    public void giveTokens(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.addTokens(amount);
        user.save();
        sender.sendMessage("You have added " + amount + " tokens to " + target.getPlayer().getName());
    }

    @Subcommand("remove")
    @Description("remove tokens from the player")
    @CommandPermission("predatorprison.tokens.remove")
    @CommandCompletion("@players @nothing")
    public void removeTokens(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.removeTokens(amount);
        user.save();
        sender.sendMessage("You have removed " + amount + " tokens from " + target.getPlayer().getName());
    }

    @Subcommand("set")
    @Description("set the player's tokens to a certain amount")
    @CommandPermission("predatorprison.tokens.set")
    @CommandCompletion("@players @nothing")
    public void setTokens(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.setTokens(amount);
        user.save();
        sender.sendMessage("You have set the tokens to " + amount + " for " + target.getPlayer().getName());
    }
}
