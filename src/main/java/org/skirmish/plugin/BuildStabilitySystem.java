package org.skirmish.plugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BuildStabilitySystem implements Listener {


    @EventHandler
    public void onPlace(BlockPlaceEvent event ){
        //getting relevant info from event
        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        final Block placedAgainst = event.getBlockAgainst();
        final boolean canBuild = event.canBuild();
        Location placedBlockLocation = block.getLocation();
        int[] placedCords = new int[]{placedBlockLocation.getBlockX(),placedBlockLocation.getBlockY(),placedBlockLocation.getBlockZ()};

        //checks if place logic returns true
        if(checkBuild(placedCords, block, placedAgainst, player)){

            PlayerInventory pi = (PlayerInventory)event.getPlayer().getInventory();

            if(block.getType() == Material.STONE && pi.containsAtLeast(new ItemStack(Material.DIAMOND), 2)){
                pi.setItemInMainHand(new ItemStack(Material.STONE));
                pi.removeItem(new ItemStack(Material.DIAMOND, 2));
                return;
            }
            if(block.getType() == Material.SMOOTH_BRICK && pi.containsAtLeast(new ItemStack(Material.DIAMOND), 3)){
                pi.setItemInMainHand(new ItemStack(Material.SMOOTH_BRICK));
                pi.removeItem(new ItemStack(Material.DIAMOND, 3));
                return;
            }
            if(block.getType() == Material.STEP && pi.containsAtLeast(new ItemStack(Material.DIAMOND), 1)){
                pi.setItemInMainHand(new ItemStack(Material.STEP));
                pi.removeItem(new ItemStack(Material.DIAMOND, 1));
                return;
            }
            if(block.getType() == Material.EMERALD_BLOCK && pi.containsAtLeast(new ItemStack(Material.EMERALD), 1)){
                pi.setItemInMainHand(new ItemStack(Material.EMERALD_BLOCK));
                pi.removeItem(new ItemStack(Material.EMERALD, 1));
                return;
            }
            if(block.getType() == Material.LEVER){
                return;
            }
            else{
                event.setCancelled(true);
            }
        }
        else{
            event.setCancelled(true);
        }
    }

    //check build stability etc
    boolean checkBuild(int[] placedCords, Block block, Block placedAgainst, Player p){
        //place walls
        if(block.getType() == Material.STONE){
            if(p.getLocation().getWorld().getBlockAt(placedCords[0],placedCords[1]-1,placedCords[2]).getType() == Material.SMOOTH_BRICK || p.getLocation().getWorld().getBlockAt(placedCords[0],placedCords[1]-1,placedCords[2]).getType() == Material.STONE){
                return true;
            }
            else{
                return false;
            }
        }
        //place foundations
        if(block.getType() == Material.SMOOTH_BRICK){
            if(placedAgainst.getType() != Material.STONE && placedAgainst.getType() != Material.STEP && placedAgainst.getType() != Material.SMOOTH_BRICK){
                return true;
            }
            else{
                return false;
            }
        }

        //place C4
        if(block.getType() == Material.LEVER){
            return true;
        }

        //place tc
        if(block.getType() == Material.EMERALD_BLOCK){
            if(p.getLocation().getWorld().getBlockAt(placedCords[0],placedCords[1]-1,placedCords[2]).getType() == Material.SMOOTH_BRICK){
                return true;
            }
            else{
                return false;
            }
        }
        //place floors
        if(block.getType() == Material.STEP){
            //initial checks to see if on wall this is directly on x and z by 1
            if(placedAgainst.getType() == Material.STONE){
                return true;
            }
            //directly on x and z by 2
            if(p.getLocation().getWorld().getBlockAt(placedCords[0]+2,placedCords[1],placedCords[2]).getType() == Material.STONE){
                return true;
            }
            if(p.getLocation().getWorld().getBlockAt(placedCords[0]-2,placedCords[1],placedCords[2]).getType() == Material.STONE){
                return true;
            }
            if(p.getLocation().getWorld().getBlockAt(placedCords[0],placedCords[1],placedCords[2]+2).getType() == Material.STONE){
                return true;
            }
            if(p.getLocation().getWorld().getBlockAt(placedCords[0],placedCords[1],placedCords[2]-2).getType() == Material.STONE){
                return true;
            }
            //diagonally by 1
            if(p.getLocation().getWorld().getBlockAt(placedCords[0]-1,placedCords[1],placedCords[2]-1).getType() == Material.STONE){
                return true;
            }
            if(p.getLocation().getWorld().getBlockAt(placedCords[0]-1,placedCords[1],placedCords[2]+1).getType() == Material.STONE){
                return true;
            }
            if(p.getLocation().getWorld().getBlockAt(placedCords[0]+1,placedCords[1],placedCords[2]+1).getType() == Material.STONE){
                return true;
            }
            if(p.getLocation().getWorld().getBlockAt(placedCords[0]+1,placedCords[1],placedCords[2]-1).getType() == Material.STONE){
                return true;
            }
            else{
                return false;
            }
        }
        //any other block
        else{
            return false;
        }
    }
}
