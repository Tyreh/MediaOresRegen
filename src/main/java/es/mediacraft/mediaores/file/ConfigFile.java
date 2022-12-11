package es.mediacraft.mediaores.file;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class ConfigFile extends AbstractFile {

    public ConfigFile(Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', getString(path, "MESSAGE " + path + " NOT FOUND"));
    }
}
