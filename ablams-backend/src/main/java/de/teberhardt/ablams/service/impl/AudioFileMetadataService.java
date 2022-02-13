package de.teberhardt.ablams.service.impl;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;

@Singleton
public class AudioFileMetadataService {

    void readAllMetadata(Path p) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        AudioFile read = AudioFileIO.read(p.toFile());
        //read.getTag().getFields().next().
    }
}
