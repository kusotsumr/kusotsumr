package musicbandlab.core.application.usecases.commands.removewherelessmusicbands;

/**
 * Интерфейс обработчика команды удаления всех элементов, меньших заданного.
 * Определяет метод для выполнения удаления элементов из коллекции.
 */
public interface RemoveWhereLessMusicBandsCommandHandler {
    public void handle(RemoveWhereLessMusicBandsCommand command);
}