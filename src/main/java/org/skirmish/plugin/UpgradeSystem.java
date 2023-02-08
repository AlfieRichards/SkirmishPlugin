package org.skirmish.plugin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class UpgradeSystem implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        if(event.getHand() == EquipmentSlot.OFF_HAND) return;

        Player p = event.getPlayer();
        PlayerInventory pi = (PlayerInventory)event.getPlayer().getInventory();

        System.out.println("1");
        //wood, stone, sheet, armour etc. Change resource and amount accordingly to upgrade, also change the removed item and set type for each one
        if(pi.getItemInMainHand().getType() == Material.BLAZE_POWDER){
            System.out.println("2");

            //fixes error thrown when clicking air
            if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR){return;}

            if(event.getClickedBlock().getType() == Material.OBSIDIAN){
                System.out.println("3");
                if(pi.containsAtLeast(new ItemStack(Material.DIAMOND), 1)){
                    System.out.println("4");
                    if(!AntiBuild.checkUpgrade(event.getClickedBlock().getLocation())){p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§l[§b§lBlock was §c§lrecently §b§ldamaged!§c§l]")); return;}
                    pi.removeItem(new ItemStack(Material.DIAMOND, 1));
                    event.getClickedBlock().setType(Material.STONE);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§l[§b§lBlock was §c§lsuccesfully §b§lupgraded!§c§l]"));
                }
                else{p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§l[§b§lYou §c§ldon't§b§l have the resources to upgrade this!§c§l]"));}
            }
            else{p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§l[§b§lThis §c§lisn't§b§l upgradable!§c§l]"));}
        }
    }
}
