package es.mediacraft.mediaores;

import es.mediacraft.mediaores.entity.TimedBlock;
import es.mediacraft.mediaores.service.EventService;
import es.mediacraft.mediaores.service.FileService;
import es.mediacraft.mediaores.util.ChatUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class MediaOres extends JavaPlugin {

    private FileService fileService;
    private EventService eventService;

    private Set<TimedBlock> timedBlocks;

    private Economy vaultEconomy;

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage("&a&l[MediaOresRegen]:  &c&cThis plugin requires WorldGuard to work!"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage("&a&l[MediaOresRegen]:  &c&cThis plugin requires Vault to work!"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) vaultEconomy = rsp.getProvider();

        timedBlocks = new LinkedHashSet<>();
        fileService = new FileService(this);
        eventService = new EventService(this);
        fileService.loadFiles();
        eventService.registerEvents();

        getServer().getScheduler().runTaskTimer(this, () -> {
            Iterator<TimedBlock> timedBlockIterator = timedBlocks.iterator();
            int restoredBlocks = 0;

            long currentTime = System.currentTimeMillis();

            long offset = 0;

            int maxBlocksPerTick = 2;
            while (timedBlockIterator.hasNext() && restoredBlocks < maxBlocksPerTick) {
                TimedBlock blockData = timedBlockIterator.next();

                if (blockData.getTimeToRegen() + offset <= currentTime) {
                    restoredBlocks++;
                    offset += 2;

                    Block block = blockData.getBlockLocation().getBlock();
                    BlockState state = block.getState();
                    state.setType(blockData.getBlockType());
                    state.update(true);

                    timedBlockIterator.remove();
                }
            }
        }, 1, 1);

        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage("    &a&lMediaOresRegen &7- &fv") + this.getDescription().getVersion() + "(Tyreh #5006)");
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage("    &aThe plugin is now enabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
    }

    @Override
    public void onDisable() {

        Iterator<TimedBlock> timedBlockIterator = timedBlocks.iterator();
        int restoredBlocks = 0;

        int maxBlocksPerTick = 2;
        while (timedBlockIterator.hasNext() && restoredBlocks < maxBlocksPerTick) {
            TimedBlock blockData = timedBlockIterator.next();
            restoredBlocks++;
            Block block = blockData.getBlockLocation().getBlock();
            BlockState state = block.getState();
            state.setType(blockData.getBlockType());
            state.update(true);
            timedBlockIterator.remove();
        }


        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage("    &a&lMediaOresRegen &7- &fv") + this.getDescription().getVersion() + "(Tyreh #5006)");
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" &cThe plugin is now disabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.colorizeMessage(" "));
        Bukkit.getConsoleSender().sendMessage();
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public Set<TimedBlock> getTimedBlocks() {
        return timedBlocks;
    }

    public void setTimedBlocks(Set<TimedBlock> timedBlocks) {
        this.timedBlocks = timedBlocks;
    }

    public Economy getVaultEconomy() {
        return vaultEconomy;
    }

    public void setVaultEconomy(Economy vaultEconomy) {
        this.vaultEconomy = vaultEconomy;
    }
}
