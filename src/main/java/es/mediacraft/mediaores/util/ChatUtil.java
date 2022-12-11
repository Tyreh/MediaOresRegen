package es.mediacraft.mediaores.util;

import org.bukkit.ChatColor;

/**
 * Utilidades para mensajes en el chat.
 */
public abstract class ChatUtil {

    /**
     * Aplica colores a un mensaje.
     * @param message Mensaje al que se le va a aplicar colores.
     * @return Mensaje con los colores ya aplicados.
     */
    public static String colorizeMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
