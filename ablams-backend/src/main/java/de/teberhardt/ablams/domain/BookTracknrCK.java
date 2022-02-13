package de.teberhardt.ablams.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

public class BookTracknrCK implements Serializable {

    private int trackNr;

    private long audiobookId;

    public int getTrackNr() {
        return trackNr;
    }

    public void setTrackNr(int trackNr) {
        this.trackNr = trackNr;
    }

    public long getAudiobookId() {
        return audiobookId;
    }

    public void setAudiobookId(long audiobookId) {
        this.audiobookId = audiobookId;
    }
}
