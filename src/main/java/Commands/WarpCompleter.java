package Commands;

import Models.WarpModel;
import Utils.WarpUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WarpCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) return null;
        WarpUtil.pInit(((Player) sender).getDisplayName());
        HashMap<String, WarpModel> warps = WarpUtil.pWarps;
        String[] _options = warps.keySet().toArray(new String[]{});
        List<String> options = new ArrayList<>(Arrays.asList(_options));
        return options;
    }
}
