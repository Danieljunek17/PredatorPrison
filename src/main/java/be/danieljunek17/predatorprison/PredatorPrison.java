package be.danieljunek17.predatorprison;

import be.danieljunek17.predatorprison.modules.currency.Currency;
import be.danieljunek17.predatorprison.modules.privatemines.PrivateMines;
import be.danieljunek17.predatorprison.storage.database.DatabaseConnection;
import be.danieljunek17.predatorprison.storage.database.GridTable;
import be.danieljunek17.predatorprison.storage.database.PrivateMineTable;
import be.danieljunek17.predatorprison.storage.database.UserTable;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PredatorPrison extends JavaPlugin {
    /**
     * The plugin instance
     */
    @Getter
    private static PredatorPrison instance;
    Currency currencyModule = new Currency();
    PrivateMines privateMinesModule = new PrivateMines();

    DatabaseConnection databaseConnection;

    @Getter
    public static UserTable userTable;
    @Getter
    public static PrivateMineTable privateMineTable;
    @Getter
    public static GridTable gridTable;
    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        enableDatabase();
        loadTables();

        commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("help");

        enableModules();
    }

    @Override
    public void onDisable() {
        disableModules();
    }

    private void enableDatabase() {
        final String host = "65.108.224.216";
        final int port = 3306;
        final String database = "s3_prisontest";
        final String username = "u3_1efv97K1jU";
        final String password = "L3JrZLYmGxx!=8j@.tKdTkip";

        databaseConnection = new DatabaseConnection(host, port, database, username, password);
    }

    private void loadTables() {
        userTable = new UserTable(databaseConnection);
        userTable.createTable();

        privateMineTable = new PrivateMineTable(databaseConnection);
        privateMineTable.createTable();

        gridTable = new GridTable(databaseConnection);
        gridTable.createTable();
    }

    private void enableModules() {
        currencyModule.enable(this, commandManager);
        privateMinesModule.enable(this, commandManager);
    }

    private void disableModules() {
        currencyModule.disable(this, commandManager);
        privateMinesModule.disable(this, commandManager);
    }
}
