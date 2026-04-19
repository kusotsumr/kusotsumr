package musicbandlab.core.application.usecases.commands.removemusicband;

/**
 * Команда для удаления музыкальной группы по ключу.
 * Содержит ключ элемента, который необходимо удалить из коллекции.
 */
public class RemoveMusicBandCommand {
    private final String key;

    public RemoveMusicBandCommand(String key) {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key should be not empty");

        this.key = key;
    }

    public String getKey() {
        return key;
    }
}