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
    public static String globalsPath = MyWarp.plugin.getDataFolder().getPath() + "/globals/globals.yaml";
    private static File gWarpsFile;
    private static YamlConfiguration gWarpsYML;
    public static HashMap<String, WarpModel> gWarps;
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
            MyWarp.Log("key: " + keys[i]);
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
        MyWarp.Log("keys:");
        for (int i = 0; i < keys.length; i++){
            MyWarp.Log(keys[i]);
        }
        MyWarp.Log("vals:");
        for (int i = 0; i < warps.length; i++){
            MyWarp.Log(warps[i].owner);
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
        init();
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
        init();
        return "200";
    }
    private static String savePrivateWarp(WarpModel privateWarp){
        init();
        return "200";
    }
    private static String deletePrivateWarp(WarpModel privateWarp){
        init();
        return "200";
    }
    public static WarpModel readWarp(String warpName, boolean isGlobal){

        if(isGlobal) return readGlobalWarp(warpName);
        return readPrivateWarp(warpName);
    }
    private static WarpModel readGlobalWarp(String warpName){
        WarpModel warp = gWarps.get(warpName);
        if(warp == null) return null;
        return warp;
    }
    private static WarpModel readPrivateWarp(String warpName){
        return null;
    }
}
