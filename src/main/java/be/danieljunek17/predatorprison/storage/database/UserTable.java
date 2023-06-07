package be.danieljunek17.predatorprison.storage.database;

import be.danieljunek17.predatorprison.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserTable {
    Connection conn;

    public UserTable(DatabaseConnection databaseConnection) {
        try {
            this.conn = databaseConnection.getConnection();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        try {
            PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `users` (`UUID` VARCHAR(40) NOT NULL,`crystals` BIGINT(30) NOT NULL DEFAULT '0',`gems` BIGINT(30) NOT NULL DEFAULT '0',`money` BIGINT(30) NOT NULL DEFAULT '0',`tokens` BIGINT(30) NOT NULL DEFAULT '0',`privateMineID` BIGINT(30) NOT NULL DEFAULT '0',PRIMARY KEY (`UUID`)) ENGINE=InnoDB;");
            stmt.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(UUID userUUID, int crystals, int gems, int money, int tokens, int privateMineID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (UUID, crystals, gems, money, tokens, privateMineID) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE crystals=VALUES(crystals), gems=VALUES(gems), money=VALUES(money), tokens=VALUES(tokens), privateMineID=VALUES(privateMineID)");
            stmt.setString(1, userUUID.toString());
            stmt.setInt(2, crystals);
            stmt.setInt(3, gems);
            stmt.setInt(4, money);
            stmt.setInt(5, tokens);
            stmt.setInt(6, privateMineID);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> load(UUID userUUID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE UUID = ?");
            stmt.setString(1, userUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return Optional.of(new User(UUID.fromString(rs.getString("uuid")), rs.getInt("crystals"), rs.getInt("gems"), rs.getInt("money"), rs.getInt("tokens"), rs.getInt("privateMineID")));
            }
            return Optional.empty();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean remove(UUID userUUID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE UUID = ?;");
            stmt.setString(1, userUUID.toString());
            return stmt.executeUpdate() == 1;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
