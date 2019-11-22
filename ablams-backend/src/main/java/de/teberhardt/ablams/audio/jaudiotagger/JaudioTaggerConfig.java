package de.teberhardt.ablams.audio.jaudiotagger;

import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JaudioTaggerConfig {

    @Bean
    @Scope("prototype")
    public AudioFileIO audioFileIO()
    {
        return AudioFileIO.getDefaultAudioFileIO();
    }
}
