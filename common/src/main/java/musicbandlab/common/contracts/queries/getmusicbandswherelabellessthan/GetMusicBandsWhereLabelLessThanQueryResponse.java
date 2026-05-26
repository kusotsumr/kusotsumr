package musicbandlab.common.contracts.queries.getmusicbandswherelabellessthan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.domain.MusicBand;

import java.util.ArrayList;

public class GetMusicBandsWhereLabelLessThanQueryResponse {
    private final ArrayList<MusicBand> musicBands;

    @JsonCreator
    public GetMusicBandsWhereLabelLessThanQueryResponse(@JsonProperty("musicBands")ArrayList<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }

    public ArrayList<MusicBand> getMusicBands() {
        return musicBands;
    }
}
