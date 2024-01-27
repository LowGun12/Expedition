package me.logan.expedition.listeners;

import me.logan.expedition.database.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final DatabaseManager databaseManager;

    public PlayerJoinListener(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();

        try {
            if (!playerExists(uuid)) {
                insertPlayerData(uuid);
            }
        } catch (SQLException var) {
            var.printStackTrace();
        }
    }




    private boolean playerExists(UUID uuid) throws SQLException {
        String query = "SELECT COUNT(*) FROM player_data WHERE UUID = ?";
        try (PreparedStatement ps = databaseManager.getConnection().prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
            return ps.executeQuery().next();
        }
    }

    private void insertPlayerData(UUID uuid) throws SQLException {
        String query = "INSERT INTO player_data (UUID, Level, Experience) VALUES (?, 0, 0)";
        try (PreparedStatement ps = databaseManager.getConnection().prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        }
    }
}
