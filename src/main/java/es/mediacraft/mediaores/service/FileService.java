package es.mediacraft.mediaores.service;

import es.mediacraft.mediaores.MediaOres;
import es.mediacraft.mediaores.file.BlockFile;
import es.mediacraft.mediaores.file.ConfigFile;
import es.mediacraft.mediaores.file.MessagesFile;

public class FileService {

    private ConfigFile configFile;
    private BlockFile blockFile;
    private MessagesFile messagesFile;

    public FileService(MediaOres plugin) {
        this.configFile = new ConfigFile(plugin, "config.yml");
        this.messagesFile = new MessagesFile(plugin, "messages.yml");
        this.blockFile = new BlockFile(plugin, "blocks.yml");
    }

    public void loadFiles() {
        configFile.load();
        messagesFile.load();
        blockFile.load();
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public BlockFile getBlockFile() {
        return blockFile;
    }

    public void setBlockFile(BlockFile blockFile) {
        this.blockFile = blockFile;
    }

    public MessagesFile getMessagesFile() {
        return messagesFile;
    }

    public void setMessagesFile(MessagesFile messagesFile) {
        this.messagesFile = messagesFile;
    }
}
