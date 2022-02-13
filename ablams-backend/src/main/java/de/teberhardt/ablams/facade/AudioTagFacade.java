package de.teberhardt.ablams.facade;

import de.teberhardt.ablams.domain.AudioCharacteristics;
import de.teberhardt.ablams.domain.Audiobook;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
public class AudioTagFacade {

    private final AudioFileIO audioFileIO;

    public AudioTagFacade(AudioFileIO audioFileIO) {
        this.audioFileIO = audioFileIO;
    }

    public void scanAudioFileForTags(Audiobook abook, de.teberhardt.ablams.domain.Audiofile domainAudioFile) throws IOException {

        Path file = Paths.get(abook.getFilePath(), domainAudioFile.getFilePath());

        try {
            AudioFile jaudioFile = audioFileIO.readFile(file.toFile());

            enrichAudioCharacteristicsFromHeader(jaudioFile.getAudioHeader(), domainAudioFile.getAudioCharacteristics());

            for (FieldKey f : FieldKey.values()) {
                TagField firstField = jaudioFile.getTag().getFirstField(f);
            }

        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            throw new IOException("Scan of AudioFile failed", ex);
        }

    }

    public void enrichAudioCharacteristics(Path p, AudioCharacteristics audioCharacteristics) throws IOException {
        enrichAudioCharacteristicsFromHeader(getAudioFileByPath(p).getAudioHeader(), audioCharacteristics);
    }

    private void enrichAudioCharacteristicsFromHeader(AudioHeader audioHeader, AudioCharacteristics audioCharacteristics)
    {
        audioCharacteristics.setChannels(audioHeader.getChannels());
        audioCharacteristics.setTrackLength(audioHeader.getTrackLength());
        audioCharacteristics.setBitRate(audioHeader.getBitRate());
        audioCharacteristics.setSampleRateAsNumber(audioHeader.getSampleRateAsNumber());
        audioCharacteristics.setEncodingType(audioHeader.getEncodingType());
    }


    private AudioFile getAudioFileByPath(Path p) throws IOException {
        try {
            return audioFileIO.readFile(p.toFile());
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            throw new IOException("Scan of AudioFile failed", ex);
        }
    }


}
