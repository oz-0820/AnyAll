package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.utils.Blocks;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MineAll {

    public static void dropOre(Player player, Location location) {

        ItemStack tool = player.getInventory().getItemInMainHand();

        List<Block> breakQueue = new LinkedList<>();
        searchOre(player.getWorld(), location, breakQueue);

        // dropItemsの都合上、空のQueueを渡す
        List<Block> breakQueueLeaves = new ArrayList<>();

        AnyAll.getPlugin().dropItems(player, tool, breakQueue, breakQueueLeaves);

    }


    public static void searchOre(World world, Location location, List<Block> breakQueue){
        location.add(-1, -1, -1);
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int targetX = 0; targetX <= 2; targetX++){
            for (int targetY = 0; targetY <= 2; targetY++) {
                for (int targetZ = 0; targetZ <= 2; targetZ++) {
                    if (!(targetX == 1 && targetY == 1 && targetZ == 1)) { // 元の座標は探索しない
                        Location nextLocation = new Location(world, x + targetX, y + targetY, z + targetZ);

                        Block nextBlock = nextLocation.getBlock();
                        if (Blocks.isOre(nextBlock.getType()) && !breakQueue.contains(nextBlock)) {
                            breakQueue.add(nextBlock);
                            searchOre(world, nextLocation, breakQueue);
                        }
                    }
                }
            }
        }

    }

}
