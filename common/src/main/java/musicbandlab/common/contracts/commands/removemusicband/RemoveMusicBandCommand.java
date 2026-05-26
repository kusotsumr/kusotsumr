package musicbandlab.common.contracts.commands.removemusicband;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.contracts.Request;import musicbandlab.common.contracts.UnitResponse; /**
 * Команда для удаления музыкальной группы по ключу.
 * Содержит ключ элемента, который необходимо удалить из коллекции.
 */
public class RemoveMusicBandCommand implements Request<UnitResponse> {
    private final String key;

    @JsonCreator
    public RemoveMusicBandCommand(@JsonProperty("key")String key) {
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key should be not empty");

        this.key = key;
    }

    public String getKey() {
        return key;
    }
}