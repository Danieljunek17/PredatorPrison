package be.danieljunek17.predatorprison.storage.database;

import be.danieljunek17.predatorprison.objects.PrivateMine;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Optional;

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
            PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `privateMines` (`id` INT(10) NOT NULL,`level` INT(3) NOT NULL,`selectedLevel` INT(3) NOT NULL,`x` INT(10) NOT NULL,`y` INT(3) NOT NULL,`z` INT(10) NOT NULL,`size` INT(10) NOT NULL DEFAULT '10',PRIMARY KEY (`id`)) ENGINE=InnoDB;");
            stmt.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int save(Integer id, int level, int selectedLevel, int x, int y, int z, int size) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO privateMines (id, level, selectedLevel, x, y, z, size) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE level=VALUES(level), selectedLevel=VALUES(selectedLevel), x=VALUES(x), y=VALUES(y), z=VALUES(z), size=VALUES(size)", Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.setInt(2, level);
            stmt.setInt(3, selectedLevel);
            stmt.setInt(4, x);
            stmt.setInt(5, y);
            stmt.setInt(6, z);
            stmt.setInt(7, size);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public Optional<PrivateMine> load(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM privateMines WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return getPrivateMine(rs, id);
            }
            return Optional.empty();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PrivateMine> getAvailablePrivateMine() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM privateMines WHERE id NOT IN (SELECT privateMineID FROM users) LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                return getPrivateMine(rs, id);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private Optional<PrivateMine> getPrivateMine(ResultSet rs, int id) throws SQLException {
        int level = rs.getInt("level");
        int selectedLevel = rs.getInt("selectedLevel");
        int x = rs.getInt("x");
        int y = rs.getInt("y");
        int z = rs.getInt("z");
        int size = rs.getInt("size");
        return Optional.of(new PrivateMine(id, level, selectedLevel, x, y, z, size));
    }
}
