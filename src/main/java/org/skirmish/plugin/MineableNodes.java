package org.skirmish.plugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MineableNodes implements Listener {
    @EventHandler
    void OnBlockBreak(BlockBreakEvent event){

        final Block block = event.getBlock();
        final Player p = event.getPlayer();
        PlayerInventory pi = (PlayerInventory)event.getPlayer().getInventory();

        //4 stages for this, one for each stage of the node. This will then be copied for all the required nodes. Stone sulf, etc
        if(block.getType() == Material.BEACON){
            event.setCancelled(true);
            pi.addItem(new ItemStack(Material.COAL, 1));
            block.setType(Material.BONE_BLOCK);
        }
        if(block.getType() == Material.BONE_BLOCK){
            event.setCancelled(true);
            pi.addItem(new ItemStack(Material.COAL, 1));
            block.setType(Material.BONE_BLOCK);
        }
    }
}
