package Commands;

import me.mywarp.MyWarp;
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

        Player player = ((Player) sender).getPlayer();
        MyWarp plugin = MyWarp.plugin;
        FileConfiguration conf = plugin.getConfig();
        Location loc = player.getLocation();
        String setBy = player.getDisplayName();

        return true;
    }
}
