package be.danieljunek17.predatorprison.storage.database;

import be.danieljunek17.predatorprison.objects.PrivateMine;
import be.danieljunek17.predatorprison.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class PrivateMineTable {
    Connection conn;

    public PrivateMineTable(DatabaseConnection databaseConnection) {
        try {
            this.conn = databaseConnection.getConnection();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        try {
            PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `privateMines` (`id` INT(10) NOT NULL,`size` INT(10) NOT NULL DEFAULT '0',PRIMARY KEY (`id`)) ENGINE=InnoDB;");
            stmt.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(int id, int size) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO privateMines (id, size) VALUES (?, ?) ON DUPLICATE KEY UPDATE size=VALUES(size)");
            stmt.setInt(1, id);
            stmt.setInt(2, size);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PrivateMine> load(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM privateMines WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return Optional.of(new PrivateMine(rs.getInt("id"), rs.getInt("size")));
            }
            return Optional.empty();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
