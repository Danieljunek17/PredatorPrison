package be.danieljunek17.predatorprison.managers;

import be.danieljunek17.predatorprison.PredatorPrison;
import be.danieljunek17.predatorprison.objects.PrivateMine;
import be.danieljunek17.predatorprison.objects.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PrivateMineManager {
    private static final Map<Integer, PrivateMine> privateMines = new HashMap<>();

    public static PrivateMine getPrivateMine(UUID uuid) {
        int privateMineID = UserManager.getUser(uuid).getPrivateMineID();
        return getPrivateMine(privateMineID);
    }
    public static PrivateMine getPrivateMine(int id) {
        if(privateMines.get(id) != null) {
            return privateMines.get(id);
        } else {
            Optional<PrivateMine> optPrivateMine = PredatorPrison.getPrivateMineTable().load(id);
            return optPrivateMine.orElseGet(() -> addPrivateMine(id));
        }
    }

    private static PrivateMine addPrivateMine(int id) {
        PrivateMine privateMine = new PrivateMine(id);
        privateMine.save();
        privateMines.put(id, privateMine);
        return privateMine;
    }

    public static void resetPrivateMine(int id) {
    }
}
