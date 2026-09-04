package musicbandlab.server.infrastructure.database;

import musicbandlab.server.api.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String HOST = "localhost";
    private static final int PORT = 5433;
    private static final String DATABASE = "studs";
    private final Config config;

    public ConnectionManager(Config config) {
        this.config = config;
    }
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE
                + "?currentSchema=" + config.getDbSchema();
        return DriverManager.getConnection(url, config.getDbUser(), config.getDbPassword());
    }
}