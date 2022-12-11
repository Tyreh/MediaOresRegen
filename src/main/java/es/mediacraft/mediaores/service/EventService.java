package es.mediacraft.mediaores.service;

import es.mediacraft.mediaores.MediaOres;
import es.mediacraft.mediaores.event.BlockBreak;
import org.bukkit.Bukkit;

public class EventService {

    private final MediaOres plugin;

    public EventService(MediaOres plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockBreak(plugin), plugin);
    }
}
