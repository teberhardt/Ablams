package de.teberhardt.ablams.web.dto;

import java.io.Serializable;

/**
 * A DTO for the Progressable entity.
 */
public class ProgressableDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long audiobookId;
    private Integer trackNr;
    private Float secondsInto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAudiobookId() {
        return audiobookId;
    }

    public void setAudiobookId(Long audiobookId) {
        this.audiobookId = audiobookId;
    }

    public Integer getTrackNr() {
        return trackNr;
    }

    public void setTrackNr(Integer trackNr) {
        this.trackNr = trackNr;
    }

    public Float getSecondsInto() {
        return secondsInto;
    }

    public void setSecondsInto(Float secondsInto) {
        this.secondsInto = secondsInto;
    }
}
