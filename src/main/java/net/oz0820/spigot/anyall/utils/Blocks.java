package net.oz0820.spigot.anyall.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Blocks {

    private static final List<Material> log = new ArrayList<>(
            Arrays.asList(
                    Material.ACACIA_LOG,
                    Material.BIRCH_LOG,
                    Material.DARK_OAK_LOG,
                    Material.JUNGLE_LOG,
                    Material.OAK_LOG,
                    Material.SPRUCE_LOG,
                    Material.RED_MUSHROOM_BLOCK,
                    Material.MUSHROOM_STEM,
                    Material.BROWN_MUSHROOM_BLOCK
            )
    );

    private static final List<Material> ore = new ArrayList<>(
            Arrays.asList(
                    Material.IRON_ORE,
                    Material.COAL_ORE,
                    Material.COPPER_ORE,
                    Material.GOLD_ORE,
                    Material.REDSTONE_ORE,
                    Material.EMERALD_ORE,
                    Material.LAPIS_ORE,
                    Material.DIAMOND_ORE,
                    Material.DEEPSLATE_IRON_ORE,
                    Material.DEEPSLATE_COAL_ORE,
                    Material.DEEPSLATE_COPPER_ORE,
                    Material.DEEPSLATE_GOLD_ORE,
                    Material.DEEPSLATE_REDSTONE_ORE,
                    Material.DEEPSLATE_EMERALD_ORE,
                    Material.DEEPSLATE_LAPIS_ORE,
                    Material.DEEPSLATE_DIAMOND_ORE,
                    Material.NETHER_GOLD_ORE,
                    Material.NETHER_QUARTZ_ORE
            )
    );

    private static final List<Material> leave = new ArrayList<>(
            Arrays.asList(
                    Material.OAK_LEAVES,
                    Material.SPRUCE_LEAVES,
                    Material.BIRCH_LEAVES,
                    Material.JUNGLE_LEAVES,
                    Material.ACACIA_LEAVES,
                    Material.DARK_OAK_LEAVES,
                    Material.AZALEA_LEAVES,
                    Material.FLOWERING_AZALEA_LEAVES
            )
    );


    public static boolean isLog(Material material) {
        return log.contains(material);
    }


    public static boolean isLeaves(Material material) {return leave.contains(material);}


    public static boolean isOre(Material material) {
        return ore.contains(material);
    }

}
