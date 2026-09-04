package musicbandlab.server.core.application.usecases;

public final class CurrentUserContext {
    private static final ThreadLocal<String> login = new ThreadLocal<>();
    private CurrentUserContext() {
    }
    public static void set(String value) {
        login.set(value);
    }
    public static String get() {
        return login.get();
    }
    public static void clear() {
        login.remove();
    }
}