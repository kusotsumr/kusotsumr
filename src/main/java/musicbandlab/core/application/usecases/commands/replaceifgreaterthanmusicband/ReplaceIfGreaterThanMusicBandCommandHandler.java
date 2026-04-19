package musicbandlab.core.application.usecases.commands.replaceifgreaterthanmusicband;

/**
 * Интерфейс обработчика команды замены элемента (если новый больше старого).
 * Определяет метод для выполнения замены по ключу с проверкой условия.
 */
public interface ReplaceIfGreaterThanMusicBandCommandHandler {
    public boolean handle(ReplaceIfGreaterThanMusicBandCommand command);
}