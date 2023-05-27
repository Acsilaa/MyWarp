package Commands;

import Models.WarpModel;
import Utils.WarpUtil;
import me.mywarp.MyWarp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class SetGlobalWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            MyWarp.Log("A player has to execute this command!");
            return true;
        }
        if(args.length != 1) return false;
        MyWarp plugin = MyWarp.plugin;
        Player player = ((Player) sender).getPlayer();
        Location warpLoc = player.getLocation();
        String gWarpName = args[0];

        WarpModel model = new WarpModel();
        model.setisGlobal(true);
        model.setOwner(player);
        model.setPosition(warpLoc);
        model.setYawPitch(warpLoc);
        model.setWorld(warpLoc);
        model.setWarpName(gWarpName);

        String result = WarpUtil.saveWarpModel(model);
        if(result.equals("200")){
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " + ChatColor.RESET +
                    ChatColor.GREEN +
                            "Successfully created warp " + "\"" + gWarpName +"\"."
            );
            return true;
        }
        if(result.equals("401")){ // name taken / already exists
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " + ChatColor.RESET +
                            ChatColor.RED +
                            "Failed to create warp " + "\"" + gWarpName +"\", because it already exists as a warp point."
            );
            return true;
        }
        player.sendMessage(
                ChatColor.YELLOW +
                        "[MyWarp] " + ChatColor.RESET +
                        ChatColor.RED +
                        "Failed to create warp " + "\"" + gWarpName +"\""
        );
        return true;
    }
}
