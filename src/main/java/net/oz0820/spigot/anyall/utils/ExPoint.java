package net.oz0820.spigot.anyall.utils;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ExPoint {
    public static int getExp(Block block, ItemStack tool){
        if (tool.getEnchantments().containsKey(Enchantment.SILK_TOUCH)){
            return 0;
        }
        switch (block.getType()){
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                return new Random().nextInt(4);

            case NETHER_GOLD_ORE:
                return new Random().nextInt(2);

            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                return new Random().nextInt(5) + 3;

            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
            case NETHER_QUARTZ_ORE:
                return new Random().nextInt(4) + 2;

            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                return new Random().nextInt(5) + 1;

            default:
                return 0;
        }
    }
}
