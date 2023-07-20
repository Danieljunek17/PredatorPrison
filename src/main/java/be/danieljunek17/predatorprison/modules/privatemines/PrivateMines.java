package be.danieljunek17.predatorprison.modules.privatemines;

import be.danieljunek17.predatorprison.modules.Module;
import be.danieljunek17.predatorprison.modules.privatemines.generators.PMineGenerator;
import be.danieljunek17.predatorprison.modules.privatemines.generators.WorldGenerator;
import be.danieljunek17.predatorprison.modules.privatemines.managers.PrivateMineManager;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static org.bukkit.Bukkit.getServer;

public class PrivateMines implements Module {
    World world;
    WorldGenerator worldGenerator = new WorldGenerator();
    PMineGenerator pMineGenerator = new PMineGenerator();

    PrivateMineManager privateMineManager = new PrivateMineManager();

    @Getter
    private final String worldName = "PrivateMines";

    @Override
    public void enable(JavaPlugin plugin, PaperCommandManager manager) {
        if(!shouldLoadCustomWorld()) {
            world = worldGenerator.createVoidWorld(worldName);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_INSOMNIA, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
        } else {
            world = Bukkit.createWorld(new WorldCreator(worldName));
        }
        manager.registerCommand(new Commands(plugin, pMineGenerator, privateMineManager, world));
    }

    @Override
    public void disable(JavaPlugin plugin, PaperCommandManager manager) {

    }

    private boolean shouldLoadCustomWorld() {
        // Check if the custom world data file exists
        return new File(getServer().getWorldContainer(), worldName).exists();
    }
}
