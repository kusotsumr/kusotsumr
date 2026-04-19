package musicbandlab.core.application.usecases.commands.clearmusicbands;

/**
 * Интерфейс обработчика команды очистки коллекции.
 * Определяет метод для удаления всех элементов из коллекции музыкальных групп.
 */
public interface ClearMusicBandsCommandHandler {
    public void handle();
}