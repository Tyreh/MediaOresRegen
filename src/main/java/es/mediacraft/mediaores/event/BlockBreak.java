package es.mediacraft.mediaores.event;

import es.mediacraft.mediaores.MediaOres;
import es.mediacraft.mediaores.entity.TimedBlock;
import es.mediacraft.mediaores.file.BlockFile;
import es.mediacraft.mediaores.file.ConfigFile;
import es.mediacraft.mediaores.file.MessagesFile;
import es.mediacraft.mediaores.util.WorldGuardUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class BlockBreak implements Listener {

    private final MediaOres plugin;
    private final ConfigFile configFile;
    private final MessagesFile messagesFile;
    private final BlockFile blockFile;

    public BlockBreak(MediaOres plugin) {
        this.plugin = plugin;
        this.configFile = plugin.getFileService().getConfigFile();
        this.messagesFile = plugin.getFileService().getMessagesFile();
        this.blockFile = plugin.getFileService().getBlockFile();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (WorldGuardUtil.playerIsInRegion(configFile, p)) {
            if (p.hasPermission(Objects.requireNonNull(configFile.getString("bypass-permission"))) && p.getGameMode() == GameMode.CREATIVE) {
                p.sendMessage(messagesFile.getMessage("bypass-warning"));
                return;
            }
            Block block = e.getBlock();
            Material material = block.getType();

            Material replaceMaterial = Material.valueOf(configFile.getString("replace-block-material"));

            e.setCancelled(true);
            for (String configMaterialNode : blockFile.getBlocks()) {
                Material configMaterial = Material.valueOf(configMaterialNode);

                if (material.equals(configMaterial)) {
                    long regenDelay = blockFile.getRegenDelay(configMaterialNode);
                    if (blockFile.isGiveDrop(configMaterialNode)) {
                        for (ItemStack item : block.getDrops()) {
                            p.getInventory().addItem(item);
                        }
                    }

                    for (String command : blockFile.getRewardCommands(configMaterialNode)) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", p.getName()));
                    }

                    blockFile.giveExpReward(p, configMaterialNode);
                    blockFile.giveMoneyReward(p, configMaterialNode);

                    TimedBlock timedBlock = new TimedBlock(e.getBlock().getState(), System.currentTimeMillis() + regenDelay);
                    plugin.getTimedBlocks().add(timedBlock);

                    block.setType(replaceMaterial);
                    block.getState().update(true);
                    break;
                }

            }
        }

    }

}
