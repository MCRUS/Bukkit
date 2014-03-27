package org.bukkit.command.defaults;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TellCommand extends VanillaCommand {
    public TellCommand() {
        super("tell");
        this.description = "Отсылает личное сообщение указанному игроку.";
        this.usageMessage = "/tell <<игрок> <сообщение>";
        this.setAliases(Arrays.asList(new String[] { "w", "msg" }));
        this.setPermission("bukkit.command.tell");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;
        if (args.length < 2)  {
            sender.sendMessage(ChatColor.RED + "Использование: " + usageMessage);
            return false;
        }

        Player player = Bukkit.getPlayerExact(args[0]);

        // If a player is hidden from the sender pretend they are offline
        if (player == null || (sender instanceof Player && !((Player) sender).canSee(player))) {
            sender.sendMessage("Игрок не найден. Возможно он не в сети.");
        } else {
            StringBuilder message = new StringBuilder();

            for (int i = 1; i < args.length; i++) {
                if (i > 1) message.append(" ");
                message.append(args[i]);
            }

            String result = ChatColor.GRAY + sender.getName() + " шепчет " + message;

            sender.sendMessage("[" + sender.getName() + "->" + player.getName() + "] " + message);
            player.sendMessage(result);
        }

        return true;
    }
}
