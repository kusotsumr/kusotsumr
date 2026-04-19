package musicbandlab.core.application.usecases.commands.removemusicband;

/**
 * Интерфейс обработчика команды удаления музыкальной группы по ключу.
 * Определяет метод для выполнения удаления элемента из коллекции.
 */
public interface RemoveMusicBandCommandHandler {
    public void handle(RemoveMusicBandCommand command);
}