package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.*;
import de.teberhardt.ablams.repository.AudioBookRepository;
import de.teberhardt.ablams.repository.AudioFileRepository;
import de.teberhardt.ablams.repository.AuthorRepository;
import org.apache.tika.metadata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static java.util.stream.Collectors.groupingBy;

@Service
public class AudioLibraryScanService {

    private final AudioFileRepository audioFileRepository;

    private final AudioBookRepository audioBookRepository;

    private final AuthorRepository authorRepository;

    private final static Logger log = LoggerFactory.getLogger(AudioLibraryScanService.class);

    private final static String AUDIO_BOOK_NAMING_PATTERN_SINGLE = "(?<Author>[\\wäöüÄÖÜß ]+) - (?<bookName>[\\wäöüÄÖÜß ]+)";

    public AudioLibraryScanService(AudioFileRepository audioFileRepository, AudioBookRepository audioBookRepository, AuthorRepository authorRepository) {
        this.audioFileRepository = audioFileRepository;

        this.audioBookRepository = audioBookRepository;
        this.authorRepository = authorRepository;
    }

    @Async
    @Transactional
    public void scan(AudioLibrary audioLibrary) throws IOException {

        Pattern p = Pattern.compile(AUDIO_BOOK_NAMING_PATTERN_SINGLE);
        Path startPath = Paths.get(audioLibrary.getFilepath()).normalize();

        Map<Path, List<Path>> collect = Files.walk(startPath)
            .parallel()
            .filter(e -> !Files.isDirectory(e))
            .filter(AudioLibraryScanService::isAudioFile)
            .collect(groupingBy(Path::getParent));

        collect
            .entrySet()
            .stream()
            .parallel()
            .map(e -> createAudiobook(e.getKey(), e.getValue()));




    }

    private AudioBook createAudiobook(Path key, List<Path> value) {
        AudioBook a = new AudioBook();
        a.setAudioFiles(createAudioFiles(value, a));
        return a;
    }

    private Set<AudioFile> createAudioFiles(List<Path> value, AudioBook a) {
       return  value.stream().map((Path e) -> createAudioFile(e, a)).collect(Collectors.toSet());
    }

    private AudioFile createAudioFile(Path e, AudioBook a) {
        AudioFile afile = new AudioFile();
        afile.setFilePath(e.toString());
        afile.setAudioBook(a);
        return afile;
    }


    private static boolean isAudioFile(Path e) {
        List<String> allowedAudioFiles = Arrays.asList("mp3");

        for (String allowedAudioFile : allowedAudioFiles) {

            if(e.getFileName().toString().toLowerCase().endsWith(allowedAudioFile.toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }

}
