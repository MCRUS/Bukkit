package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetWorldSpawnCommand extends VanillaCommand {

    public SetWorldSpawnCommand() {
        super("setworldspawn");
        this.description = "Указывает точку возрождения игроков. Если координаты не указаны то используется положение игрока исполнившего команду.";
        this.usageMessage = "/setworldspawn ИЛИ /setworldspawn <x> <y> <z>";
        this.setPermission("bukkit.command.setworldspawn");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        Player player = null;
        World world;
        if (sender instanceof Player) {
            player = (Player) sender;
            world = player.getWorld();
        } else {
            world = Bukkit.getWorlds().get(0);
        }

        final int x, y, z;

        if (args.length == 0) {
            if (player == null) {
                sender.sendMessage("Вы можете использовать эту команду только как игрок.");
                return true;
            }

            Location location = player.getLocation();

            x = location.getBlockX();
            y = location.getBlockY();
            z = location.getBlockZ();
        } else if (args.length == 3) {
            try {
                x = getInteger(sender, args[0], MIN_COORD, MAX_COORD, true);
                y = getInteger(sender, args[1], 0, world.getMaxHeight(), true);
                z = getInteger(sender, args[2], MIN_COORD, MAX_COORD, true);
            } catch (NumberFormatException ex) {
                sender.sendMessage(ex.getMessage());
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Использование: " + usageMessage);
            return false;
        }

        world.setSpawnLocation(x, y, z);

        Command.broadcastCommandMessage(sender, "Точка возрождения в мире " + world.getName() + " указана на (" + x + ", " + y + ", " + z + ")");
        return true;

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        return ImmutableList.of();
    }
}
