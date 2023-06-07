package be.danieljunek17.predatorprison.storage.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class YamlFile {
    private final JavaPlugin plugin;
    private final String name;
    private FileConfiguration fileConfiguration;
    private File file;

    public YamlFile(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;

        saveDefaultFile();
    }

    public void reload() {
        if (this.file == null)
            this.file = new File(this.plugin.getDataFolder(), name + ".yml");

        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);

        InputStream defaultStream = this.plugin.getResource(name + ".yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.fileConfiguration.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getFile() {
        if (this.fileConfiguration == null)
            reload();

        return this.fileConfiguration;
    }

    public void save() {
        if (this.fileConfiguration == null || this.file == null)
            return;

        try {
            this.fileConfiguration.save(this.file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't save the file " + this.name + ".yml.", e);
        }
    }

    public void saveDefaultFile() {
        if (this.file == null)
            this.file = new File(this.plugin.getDataFolder(), name + ".yml");

        if (!this.file.exists())
            this.plugin.saveResource(name + ".yml", false);
    }
}