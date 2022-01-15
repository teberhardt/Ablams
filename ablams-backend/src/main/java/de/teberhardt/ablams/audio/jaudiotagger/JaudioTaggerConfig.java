package de.teberhardt.ablams.audio.jaudiotagger;

import org.jaudiotagger.audio.AudioFileIO;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class JaudioTaggerConfig {

    @Produces
    public AudioFileIO audioFileIO()
    {
        return AudioFileIO.getDefaultAudioFileIO();
    }
}
