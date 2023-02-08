package org.skirmish.plugin;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

public final class AntiBuild extends JavaPlugin {
    //location, true = can place, false = no lol
    public static Map<Location, Integer> locations = new HashMap<Location, Integer>();

    //foreach value in hashmap if int != 0, int--. If == 0, remove from map
    //nothing can be repaired until the end of the timer, however blocks can be placed there half way through, altho they will be on lower health. (20s for repairing or place at full health, 10s for low health placing)


    //checks every 10 ticks for damage change (.5s)
    public static void checkMap() {
        System.out.println("c2");
        if(!locations.isEmpty()){
            try {
                for (Map.Entry<Location, Integer> entry : locations.entrySet()) {
                    Location key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println("c2");
                    //counts down the value
                    if (value > 0) {
                        value--;
                        locations.put(key, value);
                    }
                    //removes item from map after timer
                    else {
                        locations.remove(key);
                    }
                }
            }catch (Exception ignored){

            }
        }
    }

    //adds too hashmap
    public static void addKey(Location key){
        locations.put(key, 40);
    }

    //this is only used to check placability
    public static boolean checkBuild(Location key){
        if(locations.containsKey(key)){
            if(locations.get(key) != null){
                System.out.println("Recently broken or damaged");
                return false;
            }
            else{
                System.out.println("Not recently broken or damaged");
                return true;
            }
        }
        else{
            System.out.println("Not recently broken or damaged");
            return true;
        }
    }

    //this is also used to check repairability
    public static boolean checkUpgrade(Location key){
        if(locations.containsKey(key)){
            if(locations.get(key) != null || locations.get(key) > 20){
                System.out.println("Recently broken or damaged");
                return false;
            }
            else{
                System.out.println("Not recently broken or damaged");
                return true;
            }
        }
        else{
            System.out.println("Not recently broken or damaged");
            return true;
        }
    }
}