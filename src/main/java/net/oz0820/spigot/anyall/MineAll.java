package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.utils.Search;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.oz0820.spigot.anyall.utils.DropItems.dropItems;

public class MineAll {

    public static void dropOre(Player player, Block block) {

        Location location = block.getLocation();
        Material targetBlock = block.getType();
        ItemStack tool = player.getInventory().getItemInMainHand();

        List<Block> breakQueue = new ArrayList<>();
        breakQueue.add(block);
        List<Block> breakQueueLeaves = new ArrayList<>(); // BlockSearchの都合上、空のQueueを渡す

        int[] StartLocation = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};

        Search.BlockSearch(player.getWorld(), StartLocation, StartLocation, targetBlock, breakQueue, breakQueueLeaves);
        dropItems(player, tool, breakQueue, breakQueueLeaves);
    }
}
