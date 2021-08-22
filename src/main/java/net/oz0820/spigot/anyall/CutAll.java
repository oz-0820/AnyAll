package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.utils.Blocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class CutAll {

    public static void dropTree(Player player, Location location, Material targetblock) {

        ItemStack tool = player.getInventory().getItemInMainHand();

        List<Block> breakQueueLog = new LinkedList<>();
        List<Block> breakQueueLeaves = new LinkedList<>();
        searchLog(player.getWorld(), location, location, targetblock, breakQueueLog, breakQueueLeaves);

        AnyAll.getPlugin().dropItems(player, tool, breakQueueLog, breakQueueLeaves);
    }


    public static void searchLog(World world, Location location, Location basisLocation, Material targetBlock, List<Block> breakQueue, List<Block> breakQueueLeaves) {
        int x = location.getBlockX() - 1;
        int y = location.getBlockY() - 1;
        int z = location.getBlockZ() - 1;

        for (int targetX = 0; targetX < 3; targetX++) {
            for (int targetY = 0; targetY < 3; targetY++) {
                for (int targetZ = 0; targetZ < 3; targetZ++) {
                    if (!(targetX == 1 && targetY == 1 && targetZ == 1)) { // 元の座標は探索しない
                        Location nextLocation = new Location(world, x + targetX, y + targetY, z + targetZ);

                        if (
                                Math.abs(nextLocation.getBlockX() - basisLocation.getBlockX()) < 5 &&
                                Math.abs(nextLocation.getBlockZ() - basisLocation.getBlockZ()) < 5
                        ) {
                            // 初期座標から|x|,|z|<5 ブロック以上離れたら探索停止
                            Block nextBlock = nextLocation.getBlock();
                            if (targetBlock == nextBlock.getType() && !breakQueue.contains(nextBlock)) {
                                breakQueue.add(nextBlock);
                                searchLeave(world, nextLocation, breakQueueLeaves);
                                searchLog(world, nextLocation, basisLocation, targetBlock, breakQueue, breakQueueLeaves);
                            }
                        }
                    }
                }
            }
        }
    }


    public static void searchLeave(World world, Location location, List<Block> breakQueueLeaves) {
        int x = location.getBlockX() - 3;
        int y = location.getBlockY() - 3;
        int z = location.getBlockZ() - 3;

        for (int targetX = 0; targetX < 7; targetX++) {
            for (int targetY = 0; targetY < 7; targetY++) {
                for (int targetZ = 0; targetZ < 7; targetZ++) {
                    Location nextLocation = new Location(world, x + targetX, y + targetY, z + targetZ);
                    if (!(nextLocation == location)) { // 元の座標は探索しない
                        Block nextBlock = nextLocation.getBlock();
                        if (Blocks.isLeaves(nextLocation.getBlock().getType()) && !breakQueueLeaves.contains(nextBlock)) {
                            breakQueueLeaves.add(nextBlock);
                        }
                    }
                }
            }
        }
    }

}
