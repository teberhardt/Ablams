package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Cover;
import de.teberhardt.ablams.service.CoverService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CoverPhysicalScanService {

    CoverService coverService;

    public CoverPhysicalScanService(CoverService coverService) {
        this.coverService = coverService;
    }

    public Cover scanForPhysicalCoverInPath(Path folderPath) throws IOException {
        Optional<Path> optionalCover = Files.list(folderPath)
            .filter(this::isCover)
            .findAny();

        if (optionalCover.isPresent())
        {
            Cover cover = new Cover();
            cover.setFilePath(optionalCover.get().toString());
            return cover;
        }
        return null;
    }

    private boolean isCover(Path f) {
        // TODO: think about a better way to do this
       return f.getFileName().toString().endsWith(".jpg") && f.getFileName().toString().contains("Cover");
    }

}
