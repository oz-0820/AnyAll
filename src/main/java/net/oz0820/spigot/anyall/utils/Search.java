package net.oz0820.spigot.anyall.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Search {

    public static void BlockSearch(World world, int[] l, int[] StartLocation, Material targetBlock, List<Block> BreakQueue, List<Block> breakQueueLeaves) {

        Search1(world, l, StartLocation, targetBlock, BreakQueue);
        if (Blocks.isLog(targetBlock)) {
            searchLeave(world, BreakQueue, breakQueueLeaves);
        }
    }

    public static void Search1(World world, int[] l, int[] StartLocation, Material targetBlock, List<Block> BreakQueue) {

        ArrayList<int[]> SearchList = new ArrayList<>();
        for (int x = l[0] -1; x <= l[0] + 1; x++) {
            for (int y = l[1] -1; y <= l[1] + 1; y++) {
                for (int z = l[2] -1; z <= l[2] + 1; z++) {
                    // 中心は呼び出し元なので除外
                    if (!(x == l[0] & y == l[1] & z == l[2])) {
                        // 初期座標から|x|,|z|<5 ブロック以上離れたら探索停止
                        // ただし鉱石は除外
                        if (Math.abs(x - StartLocation[0]) < 5 & Math.abs(z - StartLocation[2]) < 5 | Blocks.isOre(targetBlock)) {
                            int[] var = {x, y, z};
                            SearchList.add(var);
                        }
                    }
                }
            }
        }

        for (int[] nextLocation : SearchList) {
            Block nextBlock = new Location(world, nextLocation[0], nextLocation[1], nextLocation[2]).getBlock();
            if (targetBlock == nextBlock.getType() && !BreakQueue.contains(nextBlock)) {
                BreakQueue.add(nextBlock);

                Search1(world, nextLocation, StartLocation, targetBlock, BreakQueue);
            }
        }
    }

    public static void searchLeave(World world, List<Block> BreakQueue, List<Block> breakQueueLeaves) {

        for (Block block : BreakQueue) {

            for (int x = block.getX() - 3; x <= block.getX() + 3; x++) {
                for (int y = block.getY() - 3; y <= block.getY() + 5; y++) {
                    for (int z = block.getZ() - 3; z <= block.getZ() + 3; z++) {

                        // 元の座標は探索しない
                        if (!(x == 0 & y == 0 & z == 0)) {
                            Block nextBlock = new Location(world, x, y, z).getBlock();
                            if (Blocks.isLeaves(nextBlock.getType()) && !breakQueueLeaves.contains(nextBlock)) {
                                breakQueueLeaves.add(nextBlock);
                            }
                        }
                    }
                }
            }
        }
    }
}
