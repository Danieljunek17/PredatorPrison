package be.danieljunek17.predatorprison.objects;

import be.danieljunek17.predatorprison.PredatorPrison;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private UUID uuid;
    private int crystals;
    private int gems;
    private int money;
    private int tokens;
    private int privateMineID;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.crystals = 0;
        this.gems = 0;
        this.money = 0;
        this.tokens = 0;
    }

    public void addCrystals(int amount) {
        crystals += amount;
    }

    public void addGems(int amount) {
        gems += amount;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void addTokens(int amount) {
        tokens += amount;
    }

    public void removeCrystals(int amount) {
        crystals -= amount;
    }

    public void removeGems(int amount) {
        gems -= amount;
    }

    public void removeMoney(int amount) {
        money -= amount;
    }

    public void removeTokens(int amount) {
        tokens -= amount;
    }

    public void save() {
        PredatorPrison.getUserTable().save(uuid, crystals, gems, money, tokens, privateMineID);
    }
}
