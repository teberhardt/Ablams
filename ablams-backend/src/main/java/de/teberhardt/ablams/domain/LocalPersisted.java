package de.teberhardt.ablams.domain;

import org.springframework.data.annotation.Transient;

import java.nio.file.Path;

public interface LocalPersisted {

    @Transient
    Path getPath();

}
