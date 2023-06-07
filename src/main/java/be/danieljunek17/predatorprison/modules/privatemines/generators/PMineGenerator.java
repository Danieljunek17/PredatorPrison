package be.danieljunek17.predatorprison.modules.privatemines.generators;

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

    public void generatePMine(JavaPlugin plugin, World world, String filePath, int x, int z) {
        new BukkitRunnable() {
            public void run() {
                try(EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))) {
                    Operation operation = new ClipboardHolder(loadSchematic(filePath))
                            .createPaste(editSession)
                            .to(BlockVector3.at(x, 100, z))
                            .copyEntities(true)
                            .copyBiomes(false)
                            .ignoreAirBlocks(true)
                            .build();
                    Operations.complete(operation);
                }
            }
        }.runTaskAsynchronously(plugin);
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
