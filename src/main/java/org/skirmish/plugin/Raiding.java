package org.skirmish.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Raiding implements Listener {
    @EventHandler
    void OnPlace(BlockPlaceEvent event){
        //getting relevant info from event
        final Block block = event.getBlock();
        final Player p = event.getPlayer();
        final Block placedAgainst = event.getBlockAgainst();
        final boolean canBuild = event.canBuild();
        Location placedBlockLocation = block.getLocation();
        int[] placedCords = new int[]{placedBlockLocation.getBlockX(),placedBlockLocation.getBlockY(),placedBlockLocation.getBlockZ()};

        //do an or statement for each explosive, lever is placeholder for c4
        if(block.getType() == Material.LEVER){
            ExplosiveTimer timer = new ExplosiveTimer(p, placedBlockLocation, "C4", placedCords, placedAgainst, block);
            //HandleExplosion("C4", placedCords, placedAgainst, p);
        }
    }

    public static void HandleExplosion(String explosive, int[] placedCords, Block placedAgainst, Player p) {
        //each one of these will be set to 1, 2, 3, or 4 depending on if that block is breakable and the material its made of
        int[] surroundingBlocks = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        //these represent the health of the block, 0 is unbreakable, 1 is 100%, 2 is 50%, 3 is broken (for building reasons this is relevant ig)
        int[] blockStages = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};

        //for directionality check cords of placedAgainst and compare, this way we know what side of block its placed on
        String direction = GetDirection(placedCords, placedAgainst);


        int x = -2;
        int y = 1;
        int z = -2;

        int x1 = -2;
        int y1 = 1;
        int z1 = -2;


        if (direction == "North") {
            z = 1;
            z1 = 1;
        }
        if (direction == "South") {
            z = -1;
            z1 = -1;
        }

        if (direction == "East") {
            x = -1;
            x1 = -1;
        }
        if (direction == "West") {
            x = 1;
            x1 = 1;
        }

        if (direction == "North" || direction == "South"){
            //north and south
            for (int i = 0; i <= 8;) {
                x++;

                //top left block
                Material mat = p.getLocation().getWorld().getBlockAt(placedCords[0] + x, placedCords[1] + y, placedCords[2] + z).getType();

                //Do an or statement for all of wood, so wall, floor, foundy. This reduces the checks needed, also add or statements for all damage states
                //this will need to be done 4 times for each material

                //full health
                if (mat == Material.STONE) {
                    System.out.println("BlockFound");
                    surroundingBlocks[i] = 1;
                    blockStages[i] = 1;
                }
                //half health
                if(mat == Material.COBBLESTONE){
                    System.out.println("BlockFound");
                    surroundingBlocks[i] = 1;
                    blockStages[i] = 2;
                }

                if (x == 1) {
                    x = -2;
                    y--;
                }

                i++;
            }

            //actually damaging the blocks

            for (int i1 = 0; i1 <= 8;) {
                x1++;

                //checks its not tryna set air
                if(surroundingBlocks[i1] != 0){
                    //means just broken, should never be called
                    if(blockStages[i1] == 3){

                    }
                    //half health
                    if(blockStages[i1] == 2){
                        //set to air (broken)
                        p.getLocation().getWorld().getBlockAt(placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1).setType(Material.AIR);
                        Location placedBlock = new Location(p.getLocation().getWorld(), placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1);
                        AntiBuild.addKey(placedBlock);
                        //increase damage percentage (to broken)
                        blockStages[i1] += 1;

                    }
                    //full health
                    if(blockStages[i1] == 1){
                        //set to damaged material
                        p.getLocation().getWorld().getBlockAt(placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1).setType(Material.COBBLESTONE);
                        Location placedBlock = new Location(p.getLocation().getWorld(), placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1);
                        AntiBuild.addKey(placedBlock);
                        //increase damage percentage
                        blockStages[i1] += 1;

                    }
                    //no block set meaning its terrain
                    if(blockStages[i1] == 0){

                    }

                }

                if (x1 == 1) {
                    x1 = -2;
                    y1--;
                }

                i1++;
            }
        }

        if (direction == "East" || direction == "West"){
            //east and west
            for (int i = 0; i <= 8;) {
                z++;

                //top left block
                Material mat = p.getLocation().getWorld().getBlockAt(placedCords[0] + x, placedCords[1] + y, placedCords[2] + z).getType();

                //Do an or statement for all of wood, so wall, floor, foundy. This reduces the checks needed, also add or statements for all damage states
                //this will need to be done 4 times for each material

                //full health
                if (mat == Material.STONE) {
                    System.out.println("BlockFound");
                    surroundingBlocks[i] = 1;
                    blockStages[i] = 1;
                }
                //half health
                if(mat == Material.COBBLESTONE){
                    System.out.println("BlockFound");
                    surroundingBlocks[i] = 1;
                    blockStages[i] = 2;
                }

                if (z == 1) {
                    z = -2;
                    y--;
                }

                i++;
            }

            //actually damaging the blocks

            for (int i1 = 0; i1 <= 8;) {
                z1++;

                //checks its not tryna set air
                if(surroundingBlocks[i1] != 0){
                    //means just broken, should never be called
                    if(blockStages[i1] == 3){

                    }
                    //half health
                    if(blockStages[i1] == 2){
                        //set to air (broken)
                        p.getLocation().getWorld().getBlockAt(placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1).setType(Material.AIR);
                        Location placedBlock = new Location(p.getLocation().getWorld(), placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1);
                        AntiBuild.addKey(placedBlock);
                        //increase damage percentage (to broken)
                        blockStages[i1] += 1;

                    }
                    //full health
                    if(blockStages[i1] == 1){
                        //set to damaged material
                        p.getLocation().getWorld().getBlockAt(placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1).setType(Material.COBBLESTONE);
                        Location placedBlock = new Location(p.getLocation().getWorld(), placedCords[0] + x1, placedCords[1] + y1, placedCords[2] + z1);
                        AntiBuild.addKey(placedBlock);
                        //increase damage percentage
                        blockStages[i1] += 1;

                    }
                    //no block set meaning its terrain
                    if(blockStages[i1] == 0){

                    }

                }

                if (z1 == 1) {
                    z1 = -2;
                    y1--;
                }

                i1++;
            }
        }

        //explosion effect
        p.getLocation().getWorld().createExplosion(placedCords[0], placedCords[1], placedCords[2], 3, false, false);

    }

    static String GetDirection(int[] placedCords, Block placedAgainst){
        //gets placed against cords
        Location placedAgainstLocation = placedAgainst.getLocation();
        int[] placedAgainstCords = new int[]{placedAgainstLocation.getBlockX(),placedAgainstLocation.getBlockY(),placedAgainstLocation.getBlockZ()};


        //checks X Axis
        if(placedAgainstCords[0] > placedCords[0]){
            System.out.println("West");
            return "West";
        }
        if(placedAgainstCords[0] < placedCords[0]){
            System.out.println("East");
            return "East";
        }

        //checks Y axis
        if(placedAgainstCords[1] > placedCords[1]){
            System.out.println("Bottom");
            return "Bottom";
        }
        if(placedAgainstCords[1] < placedCords[1]){
            System.out.println("Top");
            return "Top";
        }

        //checks Z axis
        if(placedAgainstCords[2] > placedCords[2]){
            System.out.println("North");
            return "North";
        }
        if(placedAgainstCords[2] < placedCords[2]){
            System.out.println("South");
            return "South";
        }
        return "Failed";
    }
}
