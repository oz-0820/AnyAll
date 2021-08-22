package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.listeners.AnyAllListener;
import net.oz0820.spigot.anyall.utils.ExPoint;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public final class AnyAll extends JavaPlugin {

    private static AnyAll plugin;

    public static AnyAll getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new AnyAllListener(), this);
    }


    public void dropItems(Player player, ItemStack tool, List<Block> breakQueue, List<Block> breakQueueLeaves) {

        ArrayList<ItemStack> drops = new ArrayList<>();
        for (Block block : breakQueue) {
            drops.addAll(block.getDrops(tool));

            int exps = ExPoint.getExp(block);
            if (exps != 0){
                player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(exps);
            }

            block.setType(Material.AIR);
        }
        for(Block block : breakQueueLeaves){
            drops.addAll(block.getDrops());
            block.setType(Material.AIR);
        }

        drops.forEach(i -> {
            if (player.getInventory().firstEmpty() != -1) {

                player.getInventory().addItem(i);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, 1);

            } else {
                player.getWorld().dropItem(player.getLocation(), i);
            }
        });
    }

}
