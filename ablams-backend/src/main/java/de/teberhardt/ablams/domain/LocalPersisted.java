package de.teberhardt.ablams.domain;

import javax.persistence.Transient;
import java.nio.file.Path;

public interface LocalPersisted {

    @Transient
    Path getPath();

}
