package musicbandlab.common.contracts.queries.getallmusicbands;

import musicbandlab.common.contracts.DataEntry;
import musicbandlab.common.domain.MusicBand;

import java.util.ArrayList;

public class GetAllMusicBandsQueryResponse implements java.io.Serializable {
    private final ArrayList<DataEntry<String, MusicBand>> musicBands;

    public GetAllMusicBandsQueryResponse(ArrayList<DataEntry<String,MusicBand>> musicBands) {
        this.musicBands = musicBands;
    }

    public ArrayList<DataEntry<String, MusicBand>> getMusicBands() {
        return musicBands;
    }
}
