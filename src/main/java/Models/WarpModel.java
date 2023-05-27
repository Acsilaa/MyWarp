package Models;


import me.mywarp.MyWarp;
import org.bukkit.World;
import org.bukkit.entity.Player;

import org.bukkit.Location;

public class WarpModel {
    public double[] position;
    public float[] yawpitch;
    public String world;
    public String owner;
    public boolean isGlobal;
    public String warpName;
    public void setPosition(Location loc){
        this.position = new double[]{
                loc.getX(),
                loc.getY(),
                loc.getZ()
        };
    }
    public void setPosition(double x, double y, double z){
        this.position = new double[]{
                x,
                y,
                z
        };
    }
    public void setYawPitch(Location loc){
        this.yawpitch = new float[]{
                loc.getYaw(),
                loc.getPitch()
        };
    }
    public void setYawPitch(float yaw, float pitch){
        this.yawpitch = new float[]{
                yaw,
                pitch
        };
    }
    public void setYawPitch(double yaw, double pitch){
        this.yawpitch = new float[]{
                (float)yaw,
                (float)pitch
        };
    }
    public void setWorld(Location loc){
        this.world = loc.getWorld().getName();
    }
    public void setWorld(String world){
        this.world = world;
    }
    public void setisGlobal(boolean bool){
        this.isGlobal = bool;
    }
    public void setWarpName(String name){
        this.warpName = name;
    }
    public void setOwner(Player player){
        this.owner = player.getDisplayName();
    }
    public void setOwner(String playerName){
        this.owner = playerName;
    }
    public boolean isGlobal(){
        return isGlobal;
    }
    public Location getLocation(){
        String world = this.world;
        double x = this.position[0];
        double y = this.position[1];
        double z = this.position[2];
        float yaw = this.yawpitch[0];
        float pitch = this.yawpitch[1];
        Location location = new Location(MyWarp.plugin.getServer().getWorld(this.world), x, y, z, yaw, pitch);
        return location;
    }
    public String getOwner(){
        return owner;
    }
    public String getWarpName(){
        return warpName;
    }
}
