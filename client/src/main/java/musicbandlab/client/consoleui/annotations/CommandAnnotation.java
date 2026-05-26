package musicbandlab.client.consoleui.annotations;

import java.lang.annotation.*;

/**
 * Аннотация для маркировки классов-команд. Используется для автоматического обнаружения команд через рефлексию.
 * Каждая команда обязана иметь уникальное ключевое слово и текст справки.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAnnotation {
    String help();
    String keyword();
}