package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.domain.AudioFile;
import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.domain.Author;
import de.teberhardt.ablams.repository.AudioBookRepository;
import de.teberhardt.ablams.repository.AudioFileRepository;
import de.teberhardt.ablams.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//        PathMatcher audioFileMatcher = FileSystems.getDefault().getPathMatcher("*.{mp3}");

        Path startPath = Paths.get(audioLibrary.getFilepath()).normalize();
        Files.walkFileTree(startPath, new FileVisitor<Path>() {

            Matcher audioBookNamingConventionMatcher;
            AudioBook scannedAbook;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                audioBookNamingConventionMatcher = p.matcher(dir.getFileName().toString());

               if(audioBookNamingConventionMatcher.matches()) {
                   scannedAbook = new AudioBook();
                   return  FileVisitResult.CONTINUE;
               }
               else
               {
                   audioBookNamingConventionMatcher = null;
                   scannedAbook = null;
                   return FileVisitResult.CONTINUE;
               }

            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               if(scannedAbook != null && file.toString().endsWith(".mp3"))
               {
                    AudioFile audioFile = new AudioFile();
                    audioFile.setFilePath(file.toString());
                    audioFile.setAudioBook(scannedAbook);
                    audioFile.setAudioLibrary(audioLibrary);
                   scannedAbook.getAudioFiles().add(audioFile);
               }
               return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                log.error("scan failed", exc);
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

                if (scannedAbook != null) {
                    String authorName = audioBookNamingConventionMatcher.group("Author");

                    Optional<Author> author = authorRepository.findAuthorByName(authorName);

                    if (!author.isPresent())
                    {
                        Author a = new Author().name(authorName);
                        author = Optional.of(authorRepository.save(a));
                    }

                    String name = audioBookNamingConventionMatcher.group("bookName");

                    scannedAbook.name(name);

                    scannedAbook = audioBookRepository.save(scannedAbook);
                    scannedAbook.getAudioFiles().forEach(audioFileRepository::save);

                    author.get().addAudioBook(scannedAbook);

                    scannedAbook = null;
                    audioBookNamingConventionMatcher = null;
                    return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
