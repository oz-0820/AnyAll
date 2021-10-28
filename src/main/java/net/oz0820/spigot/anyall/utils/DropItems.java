package net.oz0820.spigot.anyall.utils;

import net.oz0820.spigot.anyall.AnyAllConfig;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DropItems {
    public static void dropItems(Player player, ItemStack tool, List<Block> breakQueue, List<Block> breakQueueLeaves) {

        boolean digUnder = AnyAllConfig.get().getBoolean("[CustomUser]." + player.getName() + ".digUnder");
        if (AnyAllConfig.get().getString("[CustomUser]." + player.getName() + ".digUnder") == null) {
            digUnder = true;
        }
        int limitY = player.getLocation().getBlockY();

        ArrayList<ItemStack> drops = new ArrayList<>();
        int var1 = 0;
        for (Block block : breakQueue) {
            if (block.getLocation().getBlockY() >= limitY || digUnder || var1 == 0) {
                drops.addAll(block.getDrops(tool));

                int exps = ExPoint.getExp(block, tool);
                if (exps != 0) {
                    player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(exps);
                }

                block.setType(Material.AIR);
                var1 += 1;
            }
        }
        for(Block block : breakQueueLeaves) {
            drops.addAll(block.getDrops());
            block.setType(Material.AIR);
        }

        drops.forEach(i -> {
            if (player.getInventory().firstEmpty() != -1) {

                player.getInventory().addItem(i);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, 1);

            } else if (!i.getType().equals(Material.AIR)) {
                player.getWorld().dropItem(player.getLocation(), i);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0.5F, 1);
            }
        });
    }
}
