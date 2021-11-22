package net.oz0820.spigot.anyall.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlantSapling {
    public static void Plant(Player player, List<Block> breakQueueLog, Material targetMaterial) {
        Material sapling = isSapling(targetMaterial);
        if (sapling == null) {
            return;
        }

        int minY = 255;
        List<Block> PlantBlocks = new ArrayList<>();
        for (Block b : breakQueueLog) {
            int y = b.getLocation().getBlockY();
            if (y < minY) {
                PlantBlocks.clear();
                PlantBlocks.add(b);
                minY = y;
            } else if (y == minY) {
                PlantBlocks.add(b);
            }
        }

        if (!player.getInventory().contains(sapling)) {
            return;
        }

        ItemStack[] playerItemStack = player.getInventory().getContents();
        for (int i = 0; i < playerItemStack.length; i++) {
            ItemStack itemStack = playerItemStack[i];
            if (itemStack != null) {
                if (itemStack.getType() == sapling) {
                    for (int j = 0; j < itemStack.getAmount(); j++) {
                        if (PlantBlocks.size() == 0) {
                            player.getInventory().setItem(i, itemStack);
                            return;
                        }

                        Block target = PlantBlocks.remove(0);
                        Material mUnder = target.getLocation().add(0, -1, 0).getBlock().getType();
                        if (mUnder == Material.DIRT || mUnder == Material.GRASS_BLOCK) {
                            itemStack.setAmount(itemStack.getAmount() - 1);
                            target.setType(sapling);
                        }
                    }
                }
            }
            player.getInventory().setItem(i, itemStack);
        }
    }

    private static Material isSapling(Material log) {
        switch (log) {
            case ACACIA_LOG:
                return Material.ACACIA_SAPLING;
            case BIRCH_LOG:
                return Material.BIRCH_SAPLING;
            case DARK_OAK_LOG:
                return Material.DARK_OAK_SAPLING;
            case JUNGLE_LOG:
                return Material.JUNGLE_SAPLING;
            case OAK_LOG:
                return Material.OAK_SAPLING;
            case SPRUCE_LOG:
                return Material.SPRUCE_SAPLING;
            default:
                return null;
        }
    }
}

