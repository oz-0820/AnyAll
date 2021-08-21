package net.oz0820.spigot.anyall.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tools {

    private static final List<Material> pickaxe = new ArrayList<>(
            Arrays.asList(
                    Material.WOODEN_PICKAXE,
                    Material.STONE_PICKAXE,
                    Material.IRON_PICKAXE,
                    Material.GOLDEN_PICKAXE,
                    Material.DIAMOND_PICKAXE,
                    Material.NETHERITE_PICKAXE
            )
    );

    private static final List<Material> axe = new ArrayList<>(
            Arrays.asList(
                    Material.WOODEN_AXE,
                    Material.STONE_AXE,
                    Material.IRON_AXE,
                    Material.GOLDEN_AXE,
                    Material.DIAMOND_AXE,
                    Material.NETHERITE_AXE
            )
    );


    public static boolean isTools(Material material) {
        return pickaxe.contains(material) || axe.contains(material);
    }


    public static boolean isPickaxe(Material material) {
        return pickaxe.contains(material);
    }


    public static boolean isAxe(Material material) {
        return axe.contains(material);
    }

}
