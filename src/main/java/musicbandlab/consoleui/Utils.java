package musicbandlab.consoleui;

import java.util.function.Supplier;

/**
 * Вспомогательный класс с утилитными методами.
 * Содержит метод для повторного выполнения действия до успешного результата.
 */
public class Utils {
    public static <T> T retryUntilSuccess(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (Exception e) {
                System.out.println("Ошибка!");
            }
        }
    }
}