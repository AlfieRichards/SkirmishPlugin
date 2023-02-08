package org.skirmish.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class ExplosiveTimer {

    private BukkitTask task;
    private int count;

    public ExplosiveTimer(Player p, Location placedBlockLocation, String explosive, int[] placedCords, Block placedAgainst, Block block) {
        //gets plugin
        final Plugin pl = SkirmishMod.getPlugin(SkirmishMod.class);

        p.playSound(placedBlockLocation, Sound.BLOCK_DISPENSER_DISPENSE, 1f, 1f);


        count = 9; // restart count down at 10 seconds
        task = Bukkit.getScheduler().runTaskTimer(pl, () -> {
            // here what you want
            if (count == 0) {
                //runs the explosion
                block.setType(Material.AIR);
                Raiding.HandleExplosion(explosive, placedCords, placedAgainst, p);
                task.cancel(); // cancel the task if the counter is finished
                return;
            }
            //reduces the counter and plays the explosive countdown sound for lever, do an if statement for diff explosive fuse sounds
            p.playSound(placedBlockLocation, Sound.BLOCK_DISPENSER_DISPENSE, 1f, 1f);
            count--; // reduce the counter
        }, 20, 20);
    }
}
