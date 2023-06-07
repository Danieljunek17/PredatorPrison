package be.danieljunek17.predatorprison.modules;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public interface Module {

    void enable(JavaPlugin plugin, PaperCommandManager manager);
    void disable(JavaPlugin plugin, PaperCommandManager manager);
}
