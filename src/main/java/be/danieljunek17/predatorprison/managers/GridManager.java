package be.danieljunek17.predatorprison.managers;

import be.danieljunek17.predatorprison.PredatorPrison;
import be.danieljunek17.predatorprison.modules.privatemines.objects.Grid;

import java.util.Optional;

public class GridManager {
    public static Grid getGrid() {
        Optional<Grid> optGrid = PredatorPrison.getGridTable().load();
        if(optGrid.isPresent()) {
            return optGrid.get();
        } else {
            Grid grid = new Grid(0, 0, 1000);
            grid.save();
            return grid;
        }
    }
}
