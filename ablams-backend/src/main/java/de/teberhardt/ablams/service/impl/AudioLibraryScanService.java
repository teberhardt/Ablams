package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.service.AudiobookService;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.SupportedFileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class AudioLibraryScanService {

    private final Logger log = LoggerFactory.getLogger(AudioLibraryScanService.class);

    private final AudiobookService audiobookService;

    public AudioLibraryScanService(AudiobookService audiobookService) {
        this.audiobookService = audiobookService;
    }

    public void scan(AudioLibrary audioLibrary) {
        Path startPath = Paths.get(audioLibrary.getFilepath()).normalize();

        if (!Files.isDirectory(startPath))
        {
            throw new IllegalArgumentException(String.format("Given Path %s represents not a Directory", startPath.toString()));
        }

        Map<Path, List<Path>> includedFilePaths = null;
        try {
            includedFilePaths = Files.walk(startPath)
                .parallel()
                .filter(e -> !Files.isDirectory(e))
                .filter(this::isAudioFile)
                .collect(groupingBy(Path::getParent));
        } catch (IOException e) {
            log.error("Error appeared when scanning AudioLibrary on Path {}", startPath.toString(), e);
            return;
        }

        scanIncludedAudiobooks(includedFilePaths, audioLibrary);
    }

    private void scanIncludedAudiobooks(Map<Path, List<Path>> includedFilePaths, AudioLibrary audioLibrary)
    {
        //            .parallel()
        includedFilePaths
            .forEach((key, value) -> audiobookService.scan(key, value, audioLibrary));
    }

    private boolean isAudioFile(Path p)
    {
        String filename = FilenameUtils.getExtension(p.getFileName().toString());
        return isSupportedFilesuffix(filename);
    }

    private boolean isSupportedFilesuffix(String actualSuffix) {
        return Arrays
            .stream(SupportedFileFormat.values())
            .map(SupportedFileFormat::getFilesuffix)
            .anyMatch(actualSuffix::equalsIgnoreCase);
    }


}
