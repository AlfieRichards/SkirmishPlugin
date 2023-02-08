package org.skirmish.plugin;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public final class SkirmishMod extends JavaPlugin {

    public HashMap<String, Material> materials = new HashMap<>();
    private static SkirmishMod INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //branding
        LogoText();

        //registers
        init();
        startLoop();
        INSTANCE = this;
    }

    public static SkirmishMod getInstance() {
        return INSTANCE;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LogoText();
    }

    public void init() {
        //registers materials
       RegisterMaterials();
       RegisterListeners();
       this.getConfig();
    }

    public void RegisterMaterials(){
        materials.put("tc", Material.getMaterial("skirmish:toolCupboard"));
    }

    public void RegisterListeners(){
        getServer().getPluginManager().registerEvents(new UpgradeSystem(), this);
        getServer().getPluginManager().registerEvents(new Raiding(), this);
        getServer().getPluginManager().registerEvents(new MineableNodes(), this);
        getServer().getPluginManager().registerEvents(new BuildStabilitySystem(), this);
        getServer().getPluginManager().registerEvents(new LootTables(), this);
    }

    public void LogoText(){
        System.out.println("Skirmish Plugin v1.0!");
        System.out.println("Made by ArtificialIndi");
        System.out.println("Powered by sadness");
        System.out.println("   _____ _    _                _     _     ");
        System.out.println("  / ____| |  (_)              (_)   | |    ");
        System.out.println(" | (___ | | ___ _ __ _ __ ___  _ ___| |__  ");
        System.out.println("  \\___ \\| |/ / | '__| '_ ` _ \\| / __| '_ \\ ");
        System.out.println("  ____) |   <| | |  | | | | | | \\__ \\ | | |");
        System.out.println(" |_____/|_|\\_\\_|_|  |_| |_| |_|_|___/_| |_|");
        System.out.println("                                           ");
        System.out.println("                                           ");
    }

    public void startLoop() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(!AntiBuild.locations.isEmpty()){
                    AntiBuild.checkMap();
                }
            }
        }, 0L, 10L);
    }

//    public void crateRespawn(){
//        BukkitScheduler scheduler = getServer().getScheduler();
//        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
//            @Override
//            public void run() {
//                if(!LootTables.crates.isEmpty()){
//                    AntiBuild.genLoot();
//                }
//            }
//        }, 0L, 10L);
//    }
}
