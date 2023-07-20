package be.danieljunek17.predatorprison.modules.privatemines.generators;

import be.danieljunek17.predatorprison.managers.GridManager;
import be.danieljunek17.predatorprison.modules.privatemines.managers.PrivateMineManager;
import be.danieljunek17.predatorprison.modules.privatemines.objects.Grid;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class PMineGenerator {

    Grid grid = GridManager.getGrid();
    public void generatePMine(JavaPlugin plugin, PrivateMineManager privateMineManager, World world) {
        new BukkitRunnable() {
            public void run() {
                try(EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))) {
                    Operation operation = new ClipboardHolder(loadSchematic(plugin.getDataFolder() + "/schematics/Private.schem")).createPaste(editSession).to(BlockVector3.at(grid.getGridX(), 100, grid.getGridZ())).copyEntities(true).copyBiomes(false).ignoreAirBlocks(true).build();
                    Operations.complete(operation);
                    createPMine(privateMineManager, grid.getGridX(), grid.getGridZ());
                    grid.getNewGridLocation();
                    grid.save();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void generateMultiplePMines(JavaPlugin plugin, PrivateMineManager privateMineManager, World world, int amount) {
        new BukkitRunnable() {
            public void run() {
                try(EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))) {
                    for(int i = 0; i < amount; i++) {
                        Operation operation = new ClipboardHolder(loadSchematic(plugin.getDataFolder() + "/schematics/Private.schem")).createPaste(editSession).to(BlockVector3.at(grid.getGridX(), 100, grid.getGridZ())).copyEntities(true).copyBiomes(false).ignoreAirBlocks(true).build();
                        Operations.complete(operation);
                        createPMine(privateMineManager, grid.getGridX(), grid.getGridZ());
                        grid.getNewGridLocation();
                        grid.save();
                    }
                }

            }
        }.runTaskAsynchronously(plugin);
    }

    private void createPMine(PrivateMineManager privateMineManager, int x, int z) {
        privateMineManager.createPrivateMine(x, z);
    }

    private Clipboard loadSchematic(String filePath) {
        Clipboard clipboard;

        File file = new File(filePath);
        try {
            clipboard = ClipboardFormats.findByFile(file).load(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return clipboard;
    }
}
