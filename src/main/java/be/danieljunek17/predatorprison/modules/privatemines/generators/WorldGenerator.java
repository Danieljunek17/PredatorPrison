package be.danieljunek17.predatorprison.modules.privatemines.generators;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class WorldGenerator {

    public World createVoidWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        wc.generatorSettings("{\"layers\": [], \"biome\":\"plains\"}");
        return Bukkit.createWorld(wc);
    }
}
