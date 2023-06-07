package be.danieljunek17.predatorprison.modules.privatemines.objects;

import be.danieljunek17.predatorprison.PredatorPrison;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Grid {

    private int gridX;
    private int gridZ;
    private int distance;

    public void getNewGridLocation() {
        int d = distance;
        if(gridX < gridZ) {
            if(-1 * gridX < gridZ) {
                gridX += d;
                return;
            }
            gridZ += d;
            return;
        }
        if(gridX > gridZ) {
            if(-1 * gridX >= gridZ) {
                gridX -= d;
                return;
            }
            gridZ -= d;
            return;
        }
        if(gridX <= 0) {
            gridZ += d;
            return;
        }
        gridZ -= d;
    }

    public void save() {
        PredatorPrison.getGridTable().save(gridX, gridZ, distance);
    }
}