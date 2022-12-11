package es.mediacraft.mediaores.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

/**
 * Entidad que almacena temporalmente la información de un bloque para regenerarlo en un tiempo determinado en milisegundos.
 */
public class TimedBlock {
    /**
     * Tiempo de regeneración del bloque en milisegundos.
     */
    private long timeToRegen;
    /**
     * Bloque a regenerar.
     */
    private Material blockType;
    /**
     * Ubicación o coordenadas del bloque.
     */
    private Location blockLocation;

    /**
     * Almacena temporalmente la información del bloque a regenerar.
     * @param blockState Estado del bloque.
     * @param timeToRegen Tiempo de regeneración en milisegundos.
     */
    public TimedBlock(BlockState blockState, long timeToRegen) {
        this.timeToRegen = timeToRegen;
        this.blockType = blockState.getType();
        this.blockLocation = blockState.getLocation();
    }

    public long getTimeToRegen() {
        return timeToRegen;
    }

    public void setTimeToRegen(long timeToRegen) {
        this.timeToRegen = timeToRegen;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public void setBlockLocation(Location blockLocation) {
        this.blockLocation = blockLocation;
    }

    public Material getBlockType() {
        return blockType;
    }

    public void setBlockType(Material blockType) {
        this.blockType = blockType;
    }
}
