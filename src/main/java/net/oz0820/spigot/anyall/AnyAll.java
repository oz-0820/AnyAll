package net.oz0820.spigot.anyall;

import net.oz0820.spigot.anyall.Commands.AnyAllCommand;
import net.oz0820.spigot.anyall.Commands.AnyAllTabCompletion;
import net.oz0820.spigot.anyall.listeners.AnyAllListener;
import net.oz0820.spigot.anyall.utils.Search;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

import static net.oz0820.spigot.anyall.utils.DropItems.dropItems;

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

    public static void dropBlocks(Player player, Block block) {

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
