package be.danieljunek17.predatorprison.objects;

import be.danieljunek17.predatorprison.PredatorPrison;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PrivateMine {
    private int id;
    @Setter private int size;

    public PrivateMine(int id) {
        this.id = id;
        this.size = 5;
    }
    public void expandSize(int amount) {
        size += amount;
    }

    public void save() {
        PredatorPrison.getPrivateMineTable().save(id, size);
    }
}
