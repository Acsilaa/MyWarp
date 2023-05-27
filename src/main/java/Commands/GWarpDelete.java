package Commands;

import Models.WarpModel;
import Utils.WarpUtil;
import me.mywarp.MyWarp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GWarpDelete implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            MyWarp.Log("A player has to execute this command!");
            return true;
        }
        if(args.length != 1) return false;
        Player player = ((Player) sender).getPlayer();
        WarpModel warp = WarpUtil.readWarp(args[0], true);
        if(warp == null){
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " +
                    ChatColor.RED +
                            "No such warp point exists."
            );
            return true;
        }
        String process = WarpUtil.deleteWarp(warp);
        // 200, 400, 404
        if(process == "404"){
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " +
                            ChatColor.RED +
                            "No such warp point exists."
            );
            return true;
        }
        if(process == "400"){
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " +
                            ChatColor.RED +
                            "Something went very wrong."
            );
            return true;
        }
        // 200
        player.sendMessage(
                ChatColor.YELLOW +
                        "[MyWarp] " +
                        ChatColor.RED +
                        "Successfully deleted global warp point \"" + warp.getWarpName() + "\"."
        );
        return true;
    }
}
