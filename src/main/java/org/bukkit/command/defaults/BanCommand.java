package org.bukkit.command.defaults;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

public class BanCommand extends VanillaCommand {
    public BanCommand() {
        super("ban");
        this.description = "Запрещает использование сервера определнным игроком";
        this.usageMessage = "/ban <игрок> [причина ...]";
        this.setPermission("bukkit.command.ban.player");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;
        if (args.length == 0)  {
            sender.sendMessage(ChatColor.RED + "Использование: " + usageMessage);
            return false;
        }

        // TODO: Ban Reason support
        Bukkit.getOfflinePlayer(args[0]).setBanned(true);

        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            player.kickPlayer("Вы заблокированы администратором.");
        }

        Command.broadcastCommandMessage(sender, "Игрок " + args[0] + " заблокирован.");
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length >= 1) {
            return super.tabComplete(sender, alias, args);
        }
        return ImmutableList.of();
    }
}
