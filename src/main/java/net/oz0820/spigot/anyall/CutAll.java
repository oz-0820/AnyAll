package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.utils.Blocks;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class CutAll {

    public static void dropTree(Player player, Location location) {

        ItemStack tool = player.getInventory().getItemInMainHand();

        List<Block> breakQueue = new LinkedList<>();
        searchLog(player.getWorld(), location, location, breakQueue);

        AnyAll.getPlugin().dropItems(player, tool, breakQueue);

    }

    public static void searchLog(World world, Location location, Location basisLocation, List<Block> breakQueue) {

        location.add(-1, -1, -1);
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int targetX = 0; targetX <= 2; targetX++) {
            for (int targetY = 0; targetY <= 2; targetY++) {
                for (int targetZ = 0; targetZ <= 2; targetZ++) {
                    if (!(targetX == 1 && targetY == 1 && targetZ == 1)) { // 元の座標は探索しない
                        Location nextLocation = new Location(world, x + targetX, y + targetY, z + targetZ);

                        if (
                                Math.abs(nextLocation.getBlockX() - basisLocation.getBlockX()) < 5 &&
                                Math.abs(nextLocation.getBlockZ() - basisLocation.getBlockZ()) < 5 &&
                                nextLocation.getBlockY() - basisLocation.getBlockY() > -1
                        ) {
                            // 初期座標から|x|,|z|<5 y=-1ブロック以上離れたら探索停止
                            Block nextBlock = nextLocation.getBlock();
                            if (Blocks.isLog(nextBlock.getType()) && !breakQueue.contains(nextBlock)) {
                                breakQueue.add(nextBlock);
                                searchLog(world, nextLocation, basisLocation, breakQueue);
                            }
                        }

                    }
                }
            }
        }

    }

}
