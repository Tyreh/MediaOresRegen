package es.mediacraft.mediaores.file;

import es.mediacraft.mediaores.MediaOres;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BlockFile extends AbstractFile {

    private MediaOres plugin;

    public BlockFile(MediaOres plugin, String fileName) {
        super(plugin, fileName);
        this.plugin = plugin;
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', getString(path, "MESSAGE " + path + " NOT FOUND"));
    }

    public Set<String> getBlocks() {
        return getConfigurationSection("blocks").getKeys(false);
    }

    public List<String> getRewardCommands(String blockNode) {
        return getStringList("blocks." + blockNode + ".rewards.commands");
    }

    public boolean isGiveDrop(String blockNode) {
        return getBoolean("blocks." + blockNode + ".give-drop");
    }

    public void giveMoneyReward(Player p, String blockNode) {
        double amount = getDouble("blocks." + blockNode + ".money");
        if (amount > 0) {
            plugin.getVaultEconomy().depositPlayer(p, amount);
            p.sendMessage(plugin.getFileService().getMessagesFile().getMessage("money-reward"));
        }
    }

    public void giveExpReward(Player p, String blockNode) {
        float amount = (float) getDouble("blocks." + blockNode + ".experience");
        if (amount > 0) {
            p.setExp(p.getExp() + amount);
            p.sendMessage(plugin.getFileService().getMessagesFile().getMessage("experience-reward"));
        }
    }

    public long getRegenDelay(String blockNode) {
        return TimeUnit.SECONDS.toMillis(getInt("blocks." + blockNode + ".regen-delay"));
    }
}
