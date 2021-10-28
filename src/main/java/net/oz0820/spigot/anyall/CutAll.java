package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.utils.Search;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

import static net.oz0820.spigot.anyall.utils.DropItems.dropItems;

public class CutAll {

    public static void dropTree(Player player, Block block) {

        Location location = block.getLocation();
        Material targetBlock = block.getType();
        ItemStack tool = player.getInventory().getItemInMainHand();

        List<Block> breakQueueLog = new LinkedList<>();
        breakQueueLog.add(block);
        List<Block> breakQueueLeaves = new LinkedList<>();

        int[] StartLocation = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};

        Search.BlockSearch(player.getWorld(), StartLocation, StartLocation, targetBlock, breakQueueLog, breakQueueLeaves);
        dropItems(player, tool, breakQueueLog, breakQueueLeaves);
    }
}
