package org.skirmish.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LootTables implements Listener {

    public static Map<Location, Inventory> crates = new HashMap<Location, Inventory>();
    public static Map<String, Inventory> cratesTitles = new HashMap<String, Inventory>();
    public ArrayList<ItemStack[]> items = new ArrayList<ItemStack[]>();

    Material[] common = {Material.IRON_INGOT};
    Material[] rare = {Material.GOLD_INGOT};
    Material[] epic = {Material.DIAMOND};


    void GenerateChest(int type, Inventory inv, Location key){
        //new ItemStack(temp[0], 1);
        //getting lengths to calculate chances
        int commonAmnt = common.length;
        int rareAmnt = common.length;
        int epicAmnt = common.length;

        //this will hold the Semifinal itemset
        ArrayList<Material> itemSemiFinal = new ArrayList<Material>();
        //this will hold the Final itemset
        ArrayList<Material> itemFinal = new ArrayList<Material>();

        if(type == 1){
            Random rand = new Random();

            //puts random items into the first array
            for(int i = 5; i > 0; i--){
                itemSemiFinal.add(common[rand.nextInt(commonAmnt)]);
            }
            for(int i = 3; i > 0; i--){
                itemSemiFinal.add(rare[rand.nextInt(commonAmnt)]);
            }
            for(int i = 1; i > 0; i--){
                itemSemiFinal.add(epic[rand.nextInt(commonAmnt)]);
            }

            //takes random items from the first array and puts them into the second for the final item-set
            for(int i = (rand.nextInt(10)); i > 0; i--){
                //gets random items from the semi final list and adds a random amount to the final list
                itemFinal.add(itemSemiFinal.get(rand.nextInt(itemSemiFinal.size())));
            }

            for (Material item : itemFinal) {
                inv.addItem(new ItemStack(item, 1));
            }

            //adds/sets the crate in the hashmap
            crates.put(key, inv);

            //iterates the crate titles
            String original = "Crate";
            char c = ' ';
            int number = crates.size();

            char[] repeat = new char[number];
            Arrays.fill(repeat, c);
            original += new String(repeat);

            cratesTitles.put(original, inv);
        }
    }


    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        final Player p = event.getPlayer();

        if (event.getHand() == EquipmentSlot.OFF_HAND) return;


        if(block.getType() == Material.COAL_BLOCK){
            Location loc = block.getLocation();

            if(!crates.containsKey(loc)){
                Inventory inv = Bukkit.createInventory(null, 9, "Crate");
                GenerateChest(1, inv, loc);
                p.openInventory(crates.get(loc));
            }
            else{
                p.openInventory(crates.get(loc));
            }



        }

    }

    public void invClose(InventoryCloseEvent event){

        //we just have another hashmap based on inv title instead of location
        Inventory inv = event.getInventory();

        if(cratesTitles.containsKey(inv.getName())){

        }
        String name = inv.getName().substring(5);
        System.out.println("This is crate no: " + name.length());
    }
}
