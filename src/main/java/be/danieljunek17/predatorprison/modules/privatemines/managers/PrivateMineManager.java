package be.danieljunek17.predatorprison.modules.privatemines.managers;

import be.danieljunek17.predatorprison.PredatorPrison;
import be.danieljunek17.predatorprison.managers.UserManager;
import be.danieljunek17.predatorprison.objects.PrivateMine;
import be.danieljunek17.predatorprison.objects.User;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PrivateMineManager {
    private final Map<Integer, PrivateMine> privateMines = new HashMap<>();

    public PrivateMine getPrivateMine(UUID uuid) {
        User user = UserManager.getUser(uuid);
        Integer privateMineID = user.getPrivateMineID();
        if(privateMineID != null) {
            return getPrivateMine(privateMineID);
        }
        PrivateMine privateMine = getAvailableMine();
        if(privateMine != null) {
            user.setPrivateMineID(privateMine.getId());
        }
        return privateMine;
    }

    private PrivateMine getAvailableMine() {
        Optional<PrivateMine> optPrivateMine = PredatorPrison.getPrivateMineTable().getAvailablePrivateMine();
        return optPrivateMine.orElse(null);
    }

    public PrivateMine getPrivateMine(int id) {
        if(privateMines.get(id) != null) {
            return privateMines.get(id);
        } else {
            Optional<PrivateMine> optPrivateMine = PredatorPrison.getPrivateMineTable().load(id);
            return optPrivateMine.orElse(null);
        }
    }

    public PrivateMine createPrivateMine(int x, int z) {
        PrivateMine privateMine = new PrivateMine(x, z, 5);
        privateMine.save();
        privateMines.put(privateMine.getId(), privateMine);
        return privateMine;
    }

    public void resetPrivateMine(World world, PrivateMine privateMine, Map<String, Integer> blocks) {
        int size = privateMine.getSize();
        int mineLevel = privateMine.getSelectedLevel();
        int minX = privateMine.getX() - (size - 1) % 2;
        int maxX = privateMine.getX() + (size - 1) % 2;
        int minZ = privateMine.getZ() - (size - 1) % 2;
        int maxZ = privateMine.getZ() + (size - 1) % 2;
        int minY = getLowestYCoordinate(privateMine.getX(), privateMine.getZ()) + 2;
        int maxY = privateMine.getY();

        Region region = new CuboidRegion(BlockVector3.at(minX, minY, minZ), BlockVector3.at(maxX, maxY, maxZ));
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world), -1);
        editSession.setFastMode(true);

        RandomPattern pattern = new RandomPattern();
        blocks.forEach((blockName, chance) -> {
            BlockState block = BukkitAdapter.adapt(Material.getMaterial(blockName).createBlockData());
            pattern.add(block, (chance / 100D));
        });
        editSession.setBlocks(region, pattern);

        editSession.flushQueue();
    }
    public int getLowestYCoordinate(int x, int z) {
        World world = Bukkit.getWorld("PrivateMines");
        int lowestY = Integer.MAX_VALUE;

        for (int y = 0; y <= world.getMaxHeight(); y++) {
            Location location = new Location(world, x, y, z);
            Block block = world.getBlockAt(location);

            if (block.getType() == Material.BEDROCK) {
                lowestY = Math.min(lowestY, y);
            }
        }

        return lowestY;
    }
}
