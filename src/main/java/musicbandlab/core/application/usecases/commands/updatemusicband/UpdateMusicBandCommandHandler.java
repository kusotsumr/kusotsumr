package musicbandlab.core.application.usecases.commands.updatemusicband;

/**
 * Интерфейс обработчика команды обновления музыкальной группы по id.
 * Определяет метод для выполнения обновления элемента в коллекции.
 */
public interface UpdateMusicBandCommandHandler {
    public void handle(UpdateMusicBandCommand command);
}