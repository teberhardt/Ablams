package de.teberhardt.ablams.facade;

import de.teberhardt.ablams.domain.AudioCharacteristics;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class AudioTagFacade {

    private final AudioFileIO audioFileIO;

    public AudioTagFacade(AudioFileIO audioFileIO) {
        this.audioFileIO = audioFileIO;
    }

    public void scanAudioFileForTags(de.teberhardt.ablams.domain.AudioFile domainAudioFile) throws IOException {

        File file = domainAudioFile.getPath().toFile();

        try {
            AudioFile jaudioFile = audioFileIO.readFile(file);

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
