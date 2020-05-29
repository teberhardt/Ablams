package de.teberhardt.ablams.web.rest.controller;

import de.teberhardt.ablams.repository.AudiobookRepository;
import de.teberhardt.ablams.service.AudiobookService;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest()
@AutoConfigureMockMvc
class AudiobookControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    AudiobookController audiobookController;

    @Autowired
    AudiobookRepository audiobookRepository;

    @Autowired
    AudiobookService audiobookService;


    @Test
    void getAudiobook() throws Exception {

        audiobookRepository.deleteAll();
        AudiobookDTO saved = audiobookService.save(new AudiobookDTO());

        assert saved.getId() != null;
        mvc.perform(get("/api/audio-books"))
            .andDo(print())
            .andExpect(jsonPath("$.page.totalElements", is(1)))
            .andExpect(jsonPath("$._embedded.audiobookDToes[0].id", is(saved.getId().intValue())));

    }
}
