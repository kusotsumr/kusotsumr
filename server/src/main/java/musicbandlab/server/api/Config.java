package musicbandlab.server.api;

public class Config {
    private final String path;
    private final int port;

    public Config(String path, int port) {
        this.path = path;
        this.port = port;
    }

    public String getFileName() {
        return path;
    }

    public int getPort() {
        return port;
    }
}
