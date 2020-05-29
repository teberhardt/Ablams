package de.teberhardt.ablams.web.rest.controller;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest()
@AutoConfigureMockMvc
class RootControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void basic() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/api")));
    }

    @Test
    public void audiobooks() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.audio-books.href", is("http://localhost/api/audio-books")));
    }

    @Test
    public void audiofiles() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.audio-files.href", is("http://localhost/api/audio-files")));
    }

    @Test
    public void audiolibraries() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.audio-libraries.href", is("http://localhost/api/audio-libraries")));
    }

    @Test
    public void audioseries() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.audio-series.href", is("http://localhost/api/audio-series")));
    }

    @Test
    public void authors() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.authors.href", is("http://localhost/api/authors")));
    }

    @Test
    public void covers() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.covers.href", is("http://localhost/api/covers")));
    }

    @Test
    public void progresses() throws Exception {
        performRequestAndCheck()
            .andExpect(jsonPath("$._links.progresses.href", is("http://localhost/api/progresses")));
    }

    private ResultActions performRequestAndCheck() throws Exception {
        return mvc.perform(get("/api"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON));
    }
}
