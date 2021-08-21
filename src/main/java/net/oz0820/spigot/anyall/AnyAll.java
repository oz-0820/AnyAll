package net.oz0820.spigot.anyall;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;


public final class AnyAll extends JavaPlugin implements Listener{

    private List<String> var1 = new ArrayList<>();

    public AnyAll(){
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }
            if (player.isSneaking()) {
                if (tools.isTools(player.getInventory().getItemInMainHand().getType())) {
                    String p_name = player.getName();
                    if (var1.contains(p_name)) {
                        var1.remove(p_name);
                        player.sendMessage("AnyAll OFF");
                    } else {
                        var1.add(p_name);
                        player.sendMessage("AnyAll ON");
                    }
                }
            }
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!player.isSneaking()) {
            if (var1.contains(player.getName())) {

                Material tool = player.getInventory().getItemInMainHand().getType();
                Location location = e.getBlock().getLocation();

                // CutAll
                if (tools.isAxe(tool)) {
                    if (cutall.isLOG(e.getBlock().getType())) {

                        cutall.dropTree(player, location);
                    }
                }

                // MineAll
                if (tools.isPickaxe(tool)){
                    if (mineall.isOre(e.getBlock().getType())){
                        mineall.dropOre(player, location);
                    }
                }


            }
        }
    }









}
