package musicbandlab.common.contracts.commands.replaceifgreaterthanmusicband;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplaceIfGreaterThanMusicBandCommandResponse {
    private final boolean success;

    @JsonCreator
    public ReplaceIfGreaterThanMusicBandCommandResponse(@JsonProperty("success")boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
