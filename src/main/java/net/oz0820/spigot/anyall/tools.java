package net.oz0820.spigot.anyall;

import org.bukkit.Material;

public class tools {

    public static boolean isTools(Material material){
        boolean isTools = false;
        if (isAxe(material) || isPickaxe(material)){
            isTools = true;
        }
        return isTools;
    }

    public static boolean isPickaxe(Material material){
        boolean isPickaxe;
        switch (material){
            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:
                isPickaxe = true;
                break;
            default:
                isPickaxe = false;
                break;
        }
        return isPickaxe;
    }


    public static boolean isAxe(Material material) {
        boolean isAxe;
        switch (material) {
            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                isAxe = true;
                break;
            default:
                isAxe = false;
                break;
        }
        return isAxe;
    }
}
