package musicbandlab.server.core.ports;

public interface UserRepository {
    boolean register(String login, String password);
    boolean verify(String login, String password);
}