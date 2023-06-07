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
        final String host = "cayuga.bloom.host";
        final int port = 3306;
        final String database = "s32649_prisondatabase";
        final String username = "u32649_7y37wWLI2A";
        final String password = "krsctJitLuHuhrf9t9TEgJMv";

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
