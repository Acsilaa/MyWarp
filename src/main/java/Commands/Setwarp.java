package Commands;

import Models.WarpModel;
import Utils.WarpUtil;
import me.mywarp.MyWarp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Setwarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            MyWarp.Log("A player has to execute this command!");
            return true;
        }

        MyWarp plugin = MyWarp.plugin;
        Player player = ((Player) sender).getPlayer();
        Location warpLoc = player.getLocation();
        String warpName = args[0];

        WarpModel model = new WarpModel();
        model.setisGlobal(false);
        model.setOwner(player);
        model.setPosition(warpLoc);
        model.setYawPitch(warpLoc);
        model.setWorld(warpLoc);
        model.setWarpName(warpName);

        String result = WarpUtil.saveWarpModel(model);
        if(result.equals("200")){
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " + ChatColor.RESET +
                            ChatColor.GREEN +
                            "Successfully created warp " + "\"" + warpName +"\"."
            );
            return true;
        }
        if(result.equals("401")){ // name taken / already exists
            player.sendMessage(
                    ChatColor.YELLOW +
                            "[MyWarp] " + ChatColor.RESET +
                            ChatColor.RED +
                            "Failed to create warp " + "\"" + warpName +"\", because it already exists as a warp point."
            );
            return true;
        }
        player.sendMessage(
                ChatColor.YELLOW +
                        "[MyWarp] " + ChatColor.RESET +
                        ChatColor.RED +
                        "Failed to create warp " + "\"" + warpName +"\""
        );
        return true;
    }
}
