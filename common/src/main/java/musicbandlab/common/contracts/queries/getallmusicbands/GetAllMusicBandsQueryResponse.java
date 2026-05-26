package musicbandlab.common.contracts.queries.getallmusicbands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import musicbandlab.common.domain.MusicBand;

import java.util.ArrayList;
import java.util.Map;

public class GetAllMusicBandsQueryResponse {
    private final ArrayList<Map.Entry<String, MusicBand>> musicBands;

    @JsonCreator
    public GetAllMusicBandsQueryResponse(@JsonProperty("musicBands")ArrayList<Map.Entry<String,MusicBand>> musicBands) {
        this.musicBands = musicBands;
    }

    public ArrayList<Map.Entry<String, MusicBand>> getMusicBands() {
        return musicBands;
    }
}
