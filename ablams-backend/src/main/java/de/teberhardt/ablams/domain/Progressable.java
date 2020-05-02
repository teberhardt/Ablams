package de.teberhardt.ablams.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "progress")
    private Float progress;

    @Column(name = "duration")
    private Float duration;

    @OneToMany(mappedBy = "progress")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Audiofile> audiofiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getProgress() {
        return progress;
    }

    public Progressable progress(Float progress) {
        this.progress = progress;
        return this;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public Float getDuration() {
        return duration;
    }

    public Progressable duration(Float duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Set<Audiofile> getAudiofiles() {
        return audiofiles;
    }

    public Progressable audiofiles(Set<Audiofile> audiofiles) {
        this.audiofiles = audiofiles;
        return this;
    }

    public Progressable addAudiofile(Audiofile audiofile) {
        this.audiofiles.add(audiofile);
        audiofile.setProgress(this);
        return this;
    }

    public Progressable removeAudiofile(Audiofile audiofile) {
        this.audiofiles.remove(audiofile);
        audiofile.setProgress(null);
        return this;
    }

    public void setAudiofiles(Set<Audiofile> audiofiles) {
        this.audiofiles = audiofiles;
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
            "id=" + getId() +
            ", progress=" + getProgress() +
            ", duration=" + getDuration() +
            "}";
    }
}
