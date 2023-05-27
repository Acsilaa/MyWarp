package Utils;

import Models.WarpModel;
import me.mywarp.MyWarp;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WarpUtil {
    // global
    public final static String globalsPath = MyWarp.plugin.getDataFolder().getPath() + "/globals/globals.yaml";
    private static File gWarpsFile;
    private static YamlConfiguration gWarpsYML;
    public static HashMap<String, WarpModel> gWarps;
    // personal
    public final static String privatesPath = MyWarp.plugin.getDataFolder().getPath() + "/players"; // folder
    private static YamlConfiguration pWarpsYML;
    private static File playerFile;
    public static HashMap<String, WarpModel> pWarps;
    // global
    public static void init(){
        gInit();
        //pInit();
    }
    private static void gInit(){
        gWarpsFile = new File(WarpUtil.globalsPath);
        try {
            if (!gWarpsFile.exists()) {
                gWarpsFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gWarpsYML = YamlConfiguration.loadConfiguration(gWarpsFile);
        if(!gWarpsYML.isSet("warps")){
            gWarpsYML.set("warps", null);
        }
        String[] keys = new String[]{};
        try{
            keys = gWarpsYML.getConfigurationSection("warps").getKeys(false).toArray(new String[]{});
        } catch (NullPointerException e){
            //
        }
        WarpModel[] warps = new WarpModel[keys.length];
        gWarps = new HashMap<String, WarpModel>();
        for(int i = 0; i < keys.length; i++){
            WarpModel w = new WarpModel();
            w.setWorld(gWarpsYML.getString("warps." + keys[i] + ".world"));
            w.setisGlobal(gWarpsYML.getBoolean("warps." + keys[i] + ".isGlobal"));
            w.setOwner(gWarpsYML.getString("warps." + keys[i] + ".owner"));
            w.setPosition(
                    gWarpsYML.getDoubleList("warps." + keys[i] + ".position").get(0),
                    gWarpsYML.getDoubleList("warps." + keys[i] + ".position").get(1),
                    gWarpsYML.getDoubleList("warps." + keys[i] + ".position").get(2)
            );
            w.setWarpName(gWarpsYML.getString("warps." + keys[i] + ".warpName"));
            w.setYawPitch(
                    gWarpsYML.getDoubleList("warps." + keys[i] + ".yawpitch").get(0),
                    gWarpsYML.getDoubleList("warps." + keys[i] + ".yawpitch").get(1)
            );
            warps[i] = w;
            gWarps.put(keys[i], warps[i]);
        }
    }
    public static String saveWarpModel(WarpModel model){
        init();
        if(model.isGlobal()){
            return saveGlobalWarp(model);
        }
        return savePrivateWarp(model);
    }
    public static String deleteWarp(WarpModel model){
        init();
        if(model.isGlobal()){
            return deleteGlobalWarp(model);
        }
        return deletePrivateWarp(model);
    }
    private static String saveGlobalWarp(WarpModel globalWarp){
        if(gWarps.containsKey(globalWarp.getWarpName())){
            return "401";
        }
        String warpName = globalWarp.getWarpName();
        gWarpsYML.set("warps." + warpName, globalWarp);
        try {
            gWarpsYML.save(gWarpsFile);
        } catch (IOException e) {
            return "400";
        }
        gInit();
        return "200";
    }
    private static String deleteGlobalWarp(WarpModel globalWarp){
        if(!gWarps.containsKey(globalWarp.getWarpName())){
            return "404";
        }
        String warpName = globalWarp.getWarpName();
        gWarpsYML.set("warps." + warpName, null);
        try {
            gWarpsYML.save(gWarpsFile);
        } catch (IOException e) {
            return "400";
        }
        gInit();
        return "200";
    }
    public static WarpModel readWarp(String warpName, String playerName, boolean isGlobal){

        if(isGlobal) return readGlobalWarp(warpName);
        return readPrivateWarp(playerName, warpName);
    }
    private static WarpModel readGlobalWarp(String warpName){
        WarpModel warp = gWarps.get(warpName);
        if(warp == null) return null;
        return warp;
    }
    //personal
    public static void pInit(String playerName){
        playerFile = new File(privatesPath + "/" + playerName + ".yaml");
        if(!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        pWarpsYML = YamlConfiguration.loadConfiguration(playerFile);
        if(!pWarpsYML.isSet("warps")){
            pWarpsYML.set("warps", null);
        }
        String[] keys = new String[]{};
        try{
            keys = pWarpsYML.getConfigurationSection("warps").getKeys(false).toArray(new String[]{});
        } catch (NullPointerException e){
            //
        }
        WarpModel[] warps = new WarpModel[keys.length];
        pWarps = new HashMap<String, WarpModel>();
        for(int i = 0; i < keys.length; i++){
            WarpModel w = new WarpModel();
            w.setWorld(pWarpsYML.getString("warps." + keys[i] + ".world"));
            w.setisGlobal(pWarpsYML.getBoolean("warps." + keys[i] + ".isGlobal"));
            w.setOwner(pWarpsYML.getString("warps." + keys[i] + ".owner"));
            w.setPosition(
                    pWarpsYML.getDoubleList("warps." + keys[i] + ".position").get(0),
                    pWarpsYML.getDoubleList("warps." + keys[i] + ".position").get(1),
                    pWarpsYML.getDoubleList("warps." + keys[i] + ".position").get(2)
            );
            w.setWarpName(pWarpsYML.getString("warps." + keys[i] + ".warpName"));
            w.setYawPitch(
                    pWarpsYML.getDoubleList("warps." + keys[i] + ".yawpitch").get(0),
                    pWarpsYML.getDoubleList("warps." + keys[i] + ".yawpitch").get(1)
            );
            warps[i] = w;
            pWarps.put(keys[i], warps[i]);
        }
    }
    private static String savePrivateWarp(WarpModel privateWarp){
        pInit(privateWarp.getOwner());
        pWarpsYML.set("warps." + privateWarp.warpName, privateWarp);
        try {
            pWarpsYML.save(playerFile);
        } catch (IOException e) {
            return "400";
        }
        pInit(privateWarp.getOwner());
        return "200";
    }
    private static String deletePrivateWarp(WarpModel privateWarp){
        pInit(privateWarp.getOwner());
        pWarpsYML.set("warps." + privateWarp.warpName, null);
        try {
            pWarpsYML.save(playerFile);
        } catch (IOException e) {
            return "400";
        }
        pInit(privateWarp.getOwner());
        return "200";
    }
    private static WarpModel readPrivateWarp(String playerName, String warpName){
        pInit(playerName);
        WarpModel model = (WarpModel) pWarps.get(warpName);
        return model;
    }
}
