package net.oz0820.spigot.anyall.listeners;

import net.oz0820.spigot.anyall.CutAll;
import net.oz0820.spigot.anyall.MineAll;
import net.oz0820.spigot.anyall.utils.Blocks;
import net.oz0820.spigot.anyall.utils.Tools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class AnyAllListener implements Listener {

    private final List<String> mode = new ArrayList<>();

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR && player.isSneaking()) {
            if (Tools.isTools(player.getInventory().getItemInMainHand().getType())) {
                String name = player.getName();
                if (mode.contains(name)) {

                    mode.remove(name);
                    player.sendMessage("AnyAll OFF");

                } else {

                    mode.add(name);
                    player.sendMessage("AnyAll ON");

                }
            }
        }

    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (mode.contains(player.getName()) && !player.isSneaking()) {

            Material tool = player.getInventory().getItemInMainHand().getType();
            Location location = event.getBlock().getLocation();
            Material block = event.getBlock().getType();

            // CutAll
            if (Tools.isAxe(tool) && Blocks.isLog(block)) {
                event.setDropItems(false);
                CutAll.dropTree(player, location, block);
            }

            // MineAll
            if (Tools.isPickaxe(tool) && Blocks.isOre(block)) {
                event.setDropItems(false);
                MineAll.dropOre(player, location, block);
            }

        }
    }

}
