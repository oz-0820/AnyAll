package net.oz0820.spigot.anyall;

import com.sun.tools.classfile.ConstantPool;
import com.sun.tools.javac.jvm.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class cutall {

    public static void dropTree(Player player, Location location) {
        ItemStack tool = player.getInventory().getItemInMainHand();
        List<Block> blocks = new LinkedList<>();
        SearchLOG(location, location, blocks, player.getWorld());

        /*
        Player player = event.getPlayer();
        Collection<ItemStack> item = event.getBlock().getDrops(player.getInventory().getItemInMainHand());
        event.setDropItems(false);
        item.forEach(i -> player.getWorld().dropItem(player.getLocation(), i));
         */

        Collection<ItemStack> drops = new ArrayList<>();
        for (Block block : blocks) {
            // block.breakNaturally(player.getInventory().getItemInMainHand());
            drops.addAll(block.getDrops(tool));
            block.setType(Material.AIR);
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

    public static void SearchLOG(Location l, Location l1, List<Block> blocks, World world) {
        // getLogger().info("SearchLOG"); // デバッグ
        l.add(-1, -1, -1);
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();

        for (int x1 = 0; x1 <= 2; x1++) {
            for (int y1 = 0; y1 <= 2; y1++) {
                for (int z1 = 0; z1 <= 2; z1++) {
                    if (!(1 == x1 && 1 == y1 && 1 == z1)) { // 元の座標は探索しない
                        Location next = new Location(world, x + x1, y + y1, z + z1);
                        if (Math.abs(next.getBlockX() - l1.getBlockX()) < 5 &&
                                Math.abs(next.getBlockZ() - l1.getBlockZ()) < 5 &&
                                next.getBlockY() - l1.getBlockY() > -1) {
                            // 初期座標から|x|,|z|<5 y=-1ブロック以上離れたら探索停止
                            if (cutall.isLOG(next.getBlock().getType())) {
                                if (!blocks.contains(next.getBlock())) {
                                    blocks.add(next.getBlock());
                                    SearchLOG(next, l1, blocks, world);
                                }
                            }
                        }
                    }
                }
            }
        }
    }




    public static boolean isLOG(Material material){
        boolean isLOG;
        switch (material) {
            case ACACIA_LOG:
            case BIRCH_LOG:
            case DARK_OAK_LOG:
            case JUNGLE_LOG:
            case OAK_LOG:
            case SPRUCE_LOG:
            case RED_MUSHROOM_BLOCK:
            case MUSHROOM_STEM:
            case BROWN_MUSHROOM_BLOCK:
                isLOG = true;
                break;
            default:
                isLOG = false;
                break;
        }
        return isLOG;
    }
}
