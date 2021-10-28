package net.oz0820.spigot.anyall.listeners;

import net.oz0820.spigot.anyall.AnyAll;
import net.oz0820.spigot.anyall.AnyAllConfig;
import net.oz0820.spigot.anyall.utils.Blocks;
import net.oz0820.spigot.anyall.utils.Tools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class AnyAllListener implements Listener {

    private final List<String> mode = new ArrayList<>();

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action  action = event.getAction();

        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && player.isSneaking()) {
            if (Tools.isTools(player.getInventory().getItemInMainHand().getType())) {
                if (event.getHand() == EquipmentSlot.OFF_HAND) {
                    event.setCancelled(true);
                } else {
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
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (mode.contains(player.getName()) && !player.isSneaking()) {

            Material tool = player.getInventory().getItemInMainHand().getType();
            Block block = event.getBlock();
            Material material = block.getType();

            // CutAll
            if (Tools.isAxe(tool) && Blocks.isLog(material)) {
                event.setDropItems(false);
                AnyAll.dropBlocks(player, block);
            }

            // MineAll
            if (Tools.isPickaxe(tool) && Blocks.isOre(material)) {
                event.setDropItems(false);
                AnyAll.dropBlocks(player, block);
            }

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (AnyAllConfig.get().getString("[CustomUser]." + player.getName() + ".digUnder") == null) {
            AnyAllConfig.get().set("[CustomUser]." + player.getName() + ".digUnder", true);
            player.sendMessage("[AnyAll] プレイヤーより下を一括破壊範囲に含めるかどうか選択できるようになりました。\n" +
                    "'/anyall digUnder [true/false]'を利用して設定してください。\n" +
                    "デフォルトは" + ChatColor.GREEN + "TRUE" + ChatColor.WHITE + "です。");
        }

    }

}
