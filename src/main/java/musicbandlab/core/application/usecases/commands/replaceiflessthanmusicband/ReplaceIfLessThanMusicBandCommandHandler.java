package musicbandlab.core.application.usecases.commands.replaceiflessthanmusicband;

/**
 * Интерфейс обработчика команды замены элемента (если новый меньше старого).
 * Определяет метод для выполнения замены по ключу с проверкой условия.
 */
public interface ReplaceIfLessThanMusicBandCommandHandler {
    public boolean handle(ReplaceIfLessThanMusicBandCommand command);
}