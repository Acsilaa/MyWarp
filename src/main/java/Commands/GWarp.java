package Commands;

import Models.WarpModel;
import Utils.WarpUtil;
import me.mywarp.MyWarp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            MyWarp.Log("A player has to execute this command!");
            return true;
        }
        if(args.length != 1) return false;
        WarpModel model = WarpUtil.readWarp(args[0], true);
        Player player = ((Player) sender).getPlayer();
        if(model == null){
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " +
                    ChatColor.RED +
                            "No such warp point exists. Create one using /setgwarp."
            );
            return true;
        }
        Location location = model.getLocation();
        player.teleport(location);
        player.sendMessage(
                ChatColor.YELLOW +
                        "[MyWarp] " +
                ChatColor.GREEN +
                        "Teleporting to \"" + args[0] + "\"..."
        );
        return true;
    }
}
