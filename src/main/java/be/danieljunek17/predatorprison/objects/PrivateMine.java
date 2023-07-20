package be.danieljunek17.predatorprison.objects;

import be.danieljunek17.predatorprison.PredatorPrison;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PrivateMine {
    private Integer id;
    @Setter private int level;
    @Setter private int selectedLevel;
    private int x;
    private int y;
    private int z;
    @Setter private int size;

    public PrivateMine(int x, int z, int size) {
        this.id = null;
        this.level = 0;
        this.selectedLevel = 0;
        this.x = x;
        this.y = 100;
        this.z = z;
        this.size = size;
    }
    public void expandSize(int amount) {
        size += amount;
    }

    public void levelupMine() {
        level += 1;
        selectedLevel = level;
    }

    public void save() {
        id = PredatorPrison.getPrivateMineTable().save(id, level, selectedLevel, x, y, z, size);
    }
}
