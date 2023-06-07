package be.danieljunek17.predatorprison.storage.database;

import be.danieljunek17.predatorprison.modules.privatemines.objects.Grid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GridTable {
    Connection conn;

    public GridTable(DatabaseConnection databaseConnection) {
        try {
            this.conn = databaseConnection.getConnection();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        try {
            PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `grid` (`id` INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, `x` INT(10) NOT NULL DEFAULT '0',`z` INT(10) NOT NULL DEFAULT '0',`distance` INT(10) NOT NULL DEFAULT '1000') ENGINE=InnoDB;");
            stmt.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(int x, int z, int distance) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO grid (id, x, z, distance) VALUES (1, ?, ?, ?) ON DUPLICATE KEY UPDATE x=VALUES(x), z=VALUES(z), distance=VALUES(distance)");
            stmt.setInt(1, x);
            stmt.setInt(2, z);
            stmt.setInt(3, distance);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Grid> load() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM grid");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return Optional.of(new Grid(rs.getInt("x"), rs.getInt("z"), rs.getInt("distance")));
            }
            return Optional.empty();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
