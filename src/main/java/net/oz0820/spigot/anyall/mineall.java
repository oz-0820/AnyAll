package net.oz0820.spigot.anyall;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class mineall {

    public static void dropOre(Player player, Location location){
        ItemStack tool = player.getInventory().getItemInMainHand();
        List<Block> blocks = new LinkedList<>();
        SearchORE(location, location, blocks, player.getWorld());

        Collection<ItemStack> drops = new ArrayList<>();
        for (Block block : blocks) {
            // block.breakNaturally(player.getInventory().getItemInMainHand());
            drops.addAll(block.getDrops(tool));
            block.breakNaturally(tool);

        }
        drops.forEach(i -> {
            if (-1 != (player.getInventory().firstEmpty())){
                player.getInventory().addItem(i);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
            }
            else {
                player.getWorld().dropItem(player.getLocation(), i);
            }
        });
        blocks = null;

    }


    public static void SearchORE(Location l, Location l1, List<Block> blocks, World world){
        l.add(-1, -1, -1);
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();

        for (int x1 = 0; x1 <= 2; x1++){
            for (int y1 = 0; y1 <= 2; y1++) {
                for (int z1 = 0; z1 <= 2; z1++) {
                    if (!(1 == x1 && 1 == y1 && 1 == z1)) { // 元の座標は探索しない
                        Location next = new Location(world, x + x1, y + y1, z + z1);
                        if (isOre(next.getBlock().getType())) {
                            if (!blocks.contains(next.getBlock())) {
                                blocks.add(next.getBlock());
                                SearchORE(next, l1, blocks, world);
                            }
                        }
                    }
                }
            }
        }
    }



    public static boolean isOre(Material material){
        boolean isOre;
        switch (material){
            case IRON_ORE:
            case COAL_ORE:
            case COPPER_ORE:
            case GOLD_ORE:
            case REDSTONE_ORE:
            case EMERALD_ORE:
            case LAPIS_ORE:
            case DIAMOND_ORE:
            case DEEPSLATE_IRON_ORE:
            case DEEPSLATE_COAL_ORE:
            case DEEPSLATE_COPPER_ORE:
            case DEEPSLATE_GOLD_ORE:
            case DEEPSLATE_REDSTONE_ORE:
            case DEEPSLATE_EMERALD_ORE:
            case DEEPSLATE_LAPIS_ORE:
            case DEEPSLATE_DIAMOND_ORE:
            case NETHER_GOLD_ORE:
            case NETHER_QUARTZ_ORE:
                isOre = true;
                break;
            default:
                isOre = false;
                break;
        }
        return isOre;
    }
}
