package musicbandlab.common.contracts.queries.getmusicbandswherelabellessthan;

import musicbandlab.common.domain.MusicBand;

import java.util.ArrayList;

public class GetMusicBandsWhereLabelLessThanQueryResponse implements java.io.Serializable {
    private final ArrayList<MusicBand> musicBands;

    public GetMusicBandsWhereLabelLessThanQueryResponse(ArrayList<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }

    public ArrayList<MusicBand> getMusicBands() {
        return musicBands;
    }
}
