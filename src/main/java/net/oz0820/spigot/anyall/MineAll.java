package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.utils.Blocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MineAll {

    public static void dropOre(Player player, Location location, Material targetBlock) {

        ItemStack tool = player.getInventory().getItemInMainHand();

        List<Block> breakQueue = new LinkedList<>();
        searchOre(player.getWorld(), location, targetBlock, breakQueue);

        // dropItemsの都合上、空のQueueを渡す
        List<Block> breakQueueLeaves = new ArrayList<>();

        AnyAll.getPlugin().dropItems(player, tool, breakQueue, breakQueueLeaves);

    }


    public static void searchOre(World world, Location location, Material targetBlock, List<Block> breakQueue){
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        List<Location> searchList = new ArrayList<>(
                Arrays.asList(
                        new Location(world, x - 1, y, z),
                        new Location(world, x + 1, y, z),
                        new Location(world, x, y - 1, z),
                        new Location(world, x, y + 1, z),
                        new Location(world, x, y, z - 1),
                        new Location(world, x, y, z + 1)
                )
        );

        for (Location nextLocation : searchList){
            Block nextBlock = nextLocation.getBlock();
            if (Blocks.isSameOre(targetBlock, nextBlock.getType()) && !breakQueue.contains(nextBlock)){
                breakQueue.add(nextBlock);
                searchOre(world, nextLocation, targetBlock, breakQueue);
            }
        }
    }
}
