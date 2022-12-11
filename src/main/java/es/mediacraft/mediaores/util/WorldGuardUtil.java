package es.mediacraft.mediaores.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import es.mediacraft.mediaores.MediaOres;
import es.mediacraft.mediaores.file.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Utilidades con la API del plugin WorldGuard
 */
public abstract class WorldGuardUtil {

    /**
     * Verifica si un jugador se encuentra en una región que esté establecida en la configuración.
     * @param configFile Archivo de configuración principal.
     * @param p Jugador a verificar.
     * @return Si el jugador se encuentra en alguna de las regiones de la configuración o no.
     */
    public static boolean playerIsInRegion(ConfigFile configFile, Player p) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
        int x = localPlayer.getLocation().getBlockX();
        int y = localPlayer.getLocation().getBlockY();
        int z = localPlayer.getLocation().getBlockZ();

        Set<String> worldKeys = configFile.getConfigurationSection("regions").getKeys(false);
        for (String worldName : worldKeys) {
            org.bukkit.World bukkitWorld = Bukkit.getWorld(worldName);
            if (bukkitWorld == null) continue;

            World worldGuardWorld = BukkitAdapter.adapt(bukkitWorld);
            List<String> regions = configFile.getStringList("regions." + worldName);

            for (String region : regions) {
                RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager regionManager = regionContainer.get(worldGuardWorld);

                if (regionManager == null) continue;

                ProtectedRegion protectedRegion = regionManager.getRegion(region);

                if (protectedRegion == null) continue;

                if (protectedRegion.contains(x,y,z)) {
                    return true;
                }
            }
        }
        return false;
    }
}
