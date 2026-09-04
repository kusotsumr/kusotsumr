package musicbandlab.server.infrastructure.database;

import musicbandlab.server.core.ports.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserRepositoryImpl implements UserRepository {
    private final ConnectionManager connectionManager;

    public UserRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean register(String login, String password) {
        String sql = "INSERT INTO users (login, password_hash) VALUES (?, ?)";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, PasswordHasher.hash(password));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean verify(String login, String password) {
        String sql = "SELECT password_hash FROM users WHERE login = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }
                String storedHash = rs.getString("password_hash");
                return storedHash.equals(PasswordHasher.hash(password));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to verify user credentials", e);
        }
    }
}