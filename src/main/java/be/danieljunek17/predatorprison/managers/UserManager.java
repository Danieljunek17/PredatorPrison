package be.danieljunek17.predatorprison.managers;

import be.danieljunek17.predatorprison.PredatorPrison;
import be.danieljunek17.predatorprison.objects.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserManager {
    private static final Map<UUID, User> users = new HashMap<>();

    public static User getUser(UUID uuid) {
        if(users.get(uuid) != null) {
            return users.get(uuid);
        } else {
            Optional<User> optUser = PredatorPrison.getUserTable().load(uuid);
            return optUser.orElseGet(() -> addUser(uuid));
        }
    }

    private static User addUser(UUID uuid) {
        User user = new User(uuid);
        user.save();
        users.put(uuid, user);
        return user;
    }

    public static void resetUser(UUID uuid) {
        removeUser(uuid);
        addUser(uuid);
    }

    private static void removeUser(UUID uuid) {
        PredatorPrison.getUserTable().remove(uuid);
        users.remove(uuid);
    }
}
