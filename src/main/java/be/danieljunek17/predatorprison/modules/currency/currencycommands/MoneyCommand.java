package be.danieljunek17.predatorprison.modules.currency.currencycommands;

import be.danieljunek17.predatorprison.managers.UserManager;
import be.danieljunek17.predatorprison.objects.User;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("eco|money")
public class MoneyCommand extends BaseCommand {

    @Subcommand("help")
    @Description("open this menu")
    @CommandPermission("predatorprison.money.help")
    @CommandCompletion("@nothing")
    @CatchUnknown
    @HelpCommand
    public static void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Default
    @CommandAlias("bal")
    @Description("See your money")
    @CommandCompletion("@player")
    public void seeMoney(CommandSender sender, @Flags("perm=predatorprison.money.see.others") OnlinePlayer target) {
        Player player = (Player) sender;
        User user;
        if(target.getPlayer() == null) {
            user = UserManager.getUser(player.getUniqueId());
        } else {
            user = UserManager.getUser(target.getPlayer().getUniqueId());
        }

        player.sendMessage("You have " + user.getMoney() + " money");
    }

    @Subcommand("give")
    @Description("give money to player")
    @CommandPermission("predatorprison.money.give")
    @CommandCompletion("@players @nothing")
    public void giveMoney(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.addMoney(amount);
        user.save();
        sender.sendMessage("You have added " + amount + " money to " + target.getPlayer().getName());
    }

    @Subcommand("remove")
    @Description("remove money from the player")
    @CommandPermission("predatorprison.money.remove")
    @CommandCompletion("@players @nothing")
    public void removeMoney(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.removeMoney(amount);
        user.save();
        sender.sendMessage("You have removed " + amount + " money from " + target.getPlayer().getName());
    }

    @Subcommand("set")
    @Description("set the player's money to a certain amount")
    @CommandPermission("predatorprison.money.set")
    @CommandCompletion("@players @nothing")
    public void setMoney(CommandSender sender, OnlinePlayer target, Integer amount) {
        User user = UserManager.getUser(target.getPlayer().getUniqueId());

        user.setMoney(amount);
        user.save();
        sender.sendMessage("You have set the money to " + amount + " for " + target.getPlayer().getName());
    }
}
