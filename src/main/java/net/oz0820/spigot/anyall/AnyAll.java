package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.Commands.AnyAllCommand;
import net.oz0820.spigot.anyall.Commands.AnyAllTabCompletion;
import net.oz0820.spigot.anyall.listeners.AnyAllListener;
import net.oz0820.spigot.anyall.utils.ExPoint;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public final class AnyAll extends JavaPlugin {

    private static AnyAll plugin;

    public static AnyAll getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new AnyAllListener(), this);
        this.getCommand("anyall").setExecutor(new AnyAllCommand());
        this.getCommand("anyall").setTabCompleter(new AnyAllTabCompletion());
        AnyAllConfig.init();
        // AnyAllConfig.get().addDefault("[general].StartMode", true);
        // AnyAllConfig.get().addDefault("[CutAll].blockIds", "ACACIA_LOG, BIRCH_LOG, DARK_OAK_LOG, JUNGLE_LOG, OAK_LOG, SPRUCE_LOG, MUSHROOM_STEM");
        // AnyAllConfig.get().addDefault("[CutAll].leavesIds", "OAK_LEAVES, BIRCH_LEAVES, JUNGLE_LEAVES, ACACIA_LEAVES, DARK_OAK_LEAVES, AZALEA_LEAVES, FLOWERING_AZALEA_LEAVES, RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK");
        // AnyAllConfig.get().addDefault("[CutAll].itemIds", "WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE");
        // AnyAllConfig.get().addDefault("[CutAll].leavesRange", 5);
        AnyAllConfig.save();

    }


    public void dropItems(Player player, ItemStack tool, List<Block> breakQueue, List<Block> breakQueueLeaves) {

        boolean digUnder = AnyAllConfig.get().getBoolean("[CustomUser]." + player.getName() + ".digUnder");
        if (AnyAllConfig.get().getString("[CustomUser]." + player.getName() + ".digUnder") == null) {
            digUnder = true;
        }
        int limitY = player.getLocation().getBlockY();

        ArrayList<ItemStack> drops = new ArrayList<>();
        int var1 = 0;
        for (Block block : breakQueue) {
            if (block.getLocation().getBlockY() >= limitY || digUnder || var1 == 0) {
                drops.addAll(block.getDrops(tool));

                int exps = ExPoint.getExp(block, tool);
                if (exps != 0) {
                    player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(exps);
                }

                block.setType(Material.AIR);
                var1 += 1;
            }
        }
        for(Block block : breakQueueLeaves) {
            drops.addAll(block.getDrops());
            block.setType(Material.AIR);
        }

        drops.forEach(i -> {
                if (player.getInventory().firstEmpty() != -1) {

                    player.getInventory().addItem(i);
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, 1);

                } else if (!i.getType().equals(Material.AIR)) {
                    player.getWorld().dropItem(player.getLocation(), i);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0.5F, 1);
                }
        });
    }
}
