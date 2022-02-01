package net.oz0820.spigot.anyall.utils;

import net.oz0820.spigot.anyall.AnyAllConfig;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.oz0820.spigot.anyall.utils.Blocks.isLog;
import static net.oz0820.spigot.anyall.utils.Blocks.isOre;

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
            if (block.getType() != Material.AIR) {
                if (block.getLocation().getBlockY() >= limitY || digUnder || var1 == 0) {
                    drops.addAll(block.getDrops(tool));

                    int exps = ExPoint.getExp(block, tool);
                    if (exps != 0) {
                        player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(exps);
                    }

                    block.setType(Material.AIR);
                    if (isLog(block.getType())) {
                        player.playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 0.5f, 1);
                    } else if (isOre(block.getType())) {
                        player.playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 0.5f, 1);
                    }
                    var1 += 1;
                }
            }
        }
        for(Block block : breakQueueLeaves) {
            Collection<ItemStack> itemStackCollection = block.getDrops(tool);
            for (ItemStack itemStack : itemStackCollection) {
                if (itemStack.getType() != Material.AIR) {  // キノコブロックを壊すとAirがドロップすることがある
                    drops.add(itemStack);
                }
            }
            block.setType(Material.AIR);
            // player.playSound(block.getLocation(), Sound.BLOCK_GRASS_BREAK, 0.5f, 1);
            // とてもうるさかった
        }

        drops.forEach(i -> {
            Item item = player.getWorld().dropItem(player.getLocation(), i);
            item.setPickupDelay(0);
        });
    }
}
