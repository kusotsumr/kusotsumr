package musicbandlab.common.contracts.commands.replaceifgreaterthanmusicband;

public class ReplaceIfGreaterThanMusicBandCommandResponse implements java.io.Serializable {
    private final boolean success;

    public ReplaceIfGreaterThanMusicBandCommandResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
