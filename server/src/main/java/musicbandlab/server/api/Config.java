package musicbandlab.server.api;

public class Config {
    private final int port;
    private final String dbUser;
    private final String dbPassword;

    public Config(int port, String dbUser, String dbPassword) {
        this.port = port;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public int getPort() {
        return port;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    // На кафедральном сервере схема в БД совпадает с логином пользователя
    public String getDbSchema() {
        return dbUser;
    }
}