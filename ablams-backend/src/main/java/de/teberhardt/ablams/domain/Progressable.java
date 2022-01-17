package de.teberhardt.ablams.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Progressable.
 */
@Entity
@Table(name = "progressable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Progressable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "audiobook_id")
    private Long audiobookId;

    @Column(name = "trackNr")
    private Integer trackNr;

    @Column(name = "secondsInto")
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

    public void setAudiobookId(Long audiobook_id) {
        this.audiobookId = audiobook_id;
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

    @Override
    public String toString() {
        return "Progressable{" +
            "id=" + id +
            ", userId=" + userId +
            ", audiobook_id=" + audiobookId +
            ", trackNr=" + trackNr +
            ", secondsInto=" + secondsInto +
            '}';
    }
}
