package musicbandlab.common.contracts.commands.replaceiflessthanmusicband;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplaceIfLessThanMusicBandCommandResponse {
    private final boolean success;

    @JsonCreator
    public ReplaceIfLessThanMusicBandCommandResponse(@JsonProperty("success")boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
