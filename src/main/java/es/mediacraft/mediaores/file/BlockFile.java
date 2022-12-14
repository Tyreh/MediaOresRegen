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
        double amount = getDouble("blocks." + blockNode + ".rewards.money");
        if (amount > 0) {
            plugin.getVaultEconomy().depositPlayer(p, amount);
            p.sendMessage(plugin.getFileService().getMessagesFile().getMessage("money-reward").replace("%amount%", String.valueOf(amount)));
        }
    }

    public void giveExpReward(Player p, String blockNode) {
        int amount = getInt("blocks." + blockNode + ".rewards.experience");
        if (amount > 0) {
            p.giveExp(amount);
            p.sendMessage(plugin.getFileService().getMessagesFile().getMessage("experience-reward").replace("%amount%", String.valueOf(amount)));
        }
    }

    public long getRegenDelay(String blockNode) {
        return TimeUnit.SECONDS.toMillis(getInt("blocks." + blockNode + ".regen-delay"));
    }
}
