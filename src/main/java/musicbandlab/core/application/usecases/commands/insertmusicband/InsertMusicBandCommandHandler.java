package musicbandlab.core.application.usecases.commands.insertmusicband;

/**
 * Интерфейс обработчика команды вставки новой музыкальной группы.
 * Определяет метод для выполнения вставки элемента в коллекцию.
 */
public interface InsertMusicBandCommandHandler {
    public void handle(InsertMusicBandCommand command);
}