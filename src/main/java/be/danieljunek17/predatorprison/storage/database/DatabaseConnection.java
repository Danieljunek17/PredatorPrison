package be.danieljunek17.predatorprison.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    public DatabaseConnection(String host, int port, String database, String username, String password) {
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "");
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
