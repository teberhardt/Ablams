package de.teberhardt.ablams.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @OneToOne
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Audiobook audiobook;

    @OneToOne
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Audiofile audiofile;

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

    public Audiobook getAudiobook() {
        return audiobook;
    }

    public void setAudiobook(Audiobook audiobook) {
        this.audiobook = audiobook;
    }

    public Audiofile getAudiofile() {
        return audiofile;
    }

    public void setAudiofile(Audiofile audiofile) {
        this.audiofile = audiofile;
    }

    public Float getSecondsInto() {
        return secondsInto;
    }

    public void setSecondsInto(Float minutesInto) {
        this.secondsInto = minutesInto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Progressable progressable = (Progressable) o;
        if (progressable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), progressable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Progressable{" +
                "id=" + id +
                ", userId=" + userId +
                ", audiobook=" + audiobook +
                ", audiofile=" + audiofile +
                ", minutesInto=" + secondsInto +
                '}';
    }
}
