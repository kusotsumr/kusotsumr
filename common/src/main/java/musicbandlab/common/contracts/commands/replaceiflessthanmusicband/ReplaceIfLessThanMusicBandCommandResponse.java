package musicbandlab.common.contracts.commands.replaceiflessthanmusicband;

public class ReplaceIfLessThanMusicBandCommandResponse implements java.io.Serializable {
    private final boolean success;

    public ReplaceIfLessThanMusicBandCommandResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
