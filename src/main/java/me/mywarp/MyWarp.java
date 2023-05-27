package me.mywarp;

import Commands.*;
import Utils.WarpUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class MyWarp extends JavaPlugin implements Listener {

    public static MyWarp plugin;
    public static void Log(String message){
        String prefix = "[MyWarp] ";
        System.out.println(prefix + message);
    }
    public MyWarp(){plugin = this;}
    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        File globals = new File(WarpUtil.globalsPath);
        globals.getParentFile().mkdirs();
        try {
            globals.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File playerFolder = new File(plugin.getDataFolder().getPath() + "/players/");
        playerFolder.mkdirs();

        WarpUtil.init();
        getServer().getPluginManager().registerEvents(this, this);
        // set global warp
        getCommand("setglobalwarp").setExecutor(new SetGlobalWarp());
        // delete global warp
        getCommand("deleteglobalwarp").setExecutor(new GWarpDelete());
        getCommand("deleteglobalwarp").setTabCompleter(new GWarpCompleter());
        // global warp
        getCommand("globalwarp").setExecutor(new GWarp());
        getCommand("globalwarp").setTabCompleter(new GWarpCompleter());
        // setwarp
        getCommand("setwarp").setExecutor(new Setwarp());
        // warp
        getCommand("warp").setExecutor(new Warp());
        getCommand("warp").setTabCompleter(new WarpCompleter());

        Log("MyWarp has started!");
    }

    @Override
    public void onDisable() {
        Log("MyWarp is ded.");
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage(
                ChatColor.GREEN + "++ " + event.getPlayer().getDisplayName()
        );
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage(
                ChatColor.RED + "-- " + event.getPlayer().getDisplayName()
        );
    }
}
