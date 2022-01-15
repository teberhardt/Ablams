package de.teberhardt.ablams.web.dto;

import java.io.Serializable;

/**
 * A DTO for the Progressable entity.
 */
public class ProgressableDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long abookId;
    private Long afileId;
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

    public Long getAbookId() {
        return abookId;
    }

    public void setAbookId(Long abookId) {
        this.abookId = abookId;
    }

    public Long getAfileId() {
        return afileId;
    }

    public void setAfileId(Long afileId) {
        this.afileId = afileId;
    }

    public Float getSecondsInto() {
        return secondsInto;
    }

    public void setSecondsInto(Float secondsInto) {
        this.secondsInto = secondsInto;
    }
}
