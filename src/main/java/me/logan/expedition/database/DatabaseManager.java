package me.logan.expedition.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseManager {

    private final JavaPlugin plugin;
    private Connection connection;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        if (!isConnected()) {
            File configFile = new File(plugin.getDataFolder(), "database.yml");

            if (!configFile.exists()) {
                plugin.getLogger().severe("Database configuration file not found!");
                return;
            }

            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

            String host = config.getString("host");
            int port = config.getInt("port");
            String database = config.getString("name");
            String username = config.getString("username");
            String password = config.getString("password");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

            try {
                connection = DriverManager.getConnection(url, username, password);
                plugin.getLogger().info("Connected to the database!");
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to connect to the database: " + e.getMessage());
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                plugin.getLogger().info("Disconnected from the database!");
                createTable();
            } catch (SQLException e) {
                plugin.getLogger().severe("Error while disconnecting from the database: " + e.getMessage());
            }
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }



    private void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS player_data ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                + "UUID VARCHAR(36) NOT NULL,"
                + "Level INT CHECK (Level >= 0 AND Level <= 100),"
                + "Experience INT CHECK (Experience >= 0 AND Experience <= 100000)"
                + ");";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.executeUpdate();
            plugin.getLogger().info("Player data table created or already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
