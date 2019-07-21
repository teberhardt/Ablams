package de.teberhardt.ablams.web.rest;

import de.teberhardt.ablams.AblamsApp;

import de.teberhardt.ablams.domain.AudioSeries;
import de.teberhardt.ablams.repository.AudioSeriesRepository;
import de.teberhardt.ablams.service.AudioSeriesService;
import de.teberhardt.ablams.service.dto.AudioSeriesDTO;
import de.teberhardt.ablams.service.mapper.AudioSeriesMapper;
import de.teberhardt.ablams.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static de.teberhardt.ablams.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AudioSeriesResource REST controller.
 *
 * @see AudioSeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AblamsApp.class)
public class AudioSeriesResourceIntTest {

    private static final String DEFAULT_SERIES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERIES_NAME = "BBBBBBBBBB";

    @Autowired
    private AudioSeriesRepository audioSeriesRepository;

    @Autowired
    private AudioSeriesMapper audioSeriesMapper;

    @Autowired
    private AudioSeriesService audioSeriesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAudioSeriesMockMvc;

    private AudioSeries audioSeries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AudioSeriesResource audioSeriesResource = new AudioSeriesResource(audioSeriesService);
        this.restAudioSeriesMockMvc = MockMvcBuilders.standaloneSetup(audioSeriesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudioSeries createEntity(EntityManager em) {
        AudioSeries audioSeries = new AudioSeries()
            .seriesName(DEFAULT_SERIES_NAME);
        return audioSeries;
    }

    @Before
    public void initTest() {
        audioSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudioSeries() throws Exception {
        int databaseSizeBeforeCreate = audioSeriesRepository.findAll().size();

        // Create the AudioSeries
        AudioSeriesDTO audioSeriesDTO = audioSeriesMapper.toDto(audioSeries);
        restAudioSeriesMockMvc.perform(post("/api/audio-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioSeriesDTO)))
            .andExpect(status().isCreated());

        // Validate the AudioSeries in the database
        List<AudioSeries> audioSeriesList = audioSeriesRepository.findAll();
        assertThat(audioSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        AudioSeries testAudioSeries = audioSeriesList.get(audioSeriesList.size() - 1);
        assertThat(testAudioSeries.getSeriesName()).isEqualTo(DEFAULT_SERIES_NAME);
    }

    @Test
    @Transactional
    public void createAudioSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioSeriesRepository.findAll().size();

        // Create the AudioSeries with an existing ID
        audioSeries.setId(1L);
        AudioSeriesDTO audioSeriesDTO = audioSeriesMapper.toDto(audioSeries);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioSeriesMockMvc.perform(post("/api/audio-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioSeriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioSeries in the database
        List<AudioSeries> audioSeriesList = audioSeriesRepository.findAll();
        assertThat(audioSeriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudioSeries() throws Exception {
        // Initialize the database
        audioSeriesRepository.saveAndFlush(audioSeries);

        // Get all the audioSeriesList
        restAudioSeriesMockMvc.perform(get("/api/audio-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audioSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].seriesName").value(hasItem(DEFAULT_SERIES_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getAudioSeries() throws Exception {
        // Initialize the database
        audioSeriesRepository.saveAndFlush(audioSeries);

        // Get the audioSeries
        restAudioSeriesMockMvc.perform(get("/api/audio-series/{id}", audioSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audioSeries.getId().intValue()))
            .andExpect(jsonPath("$.seriesName").value(DEFAULT_SERIES_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudioSeries() throws Exception {
        // Get the audioSeries
        restAudioSeriesMockMvc.perform(get("/api/audio-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudioSeries() throws Exception {
        // Initialize the database
        audioSeriesRepository.saveAndFlush(audioSeries);

        int databaseSizeBeforeUpdate = audioSeriesRepository.findAll().size();

        // Update the audioSeries
        AudioSeries updatedAudioSeries = audioSeriesRepository.findById(audioSeries.getId()).get();
        // Disconnect from session so that the updates on updatedAudioSeries are not directly saved in db
        em.detach(updatedAudioSeries);
        updatedAudioSeries
            .seriesName(UPDATED_SERIES_NAME);
        AudioSeriesDTO audioSeriesDTO = audioSeriesMapper.toDto(updatedAudioSeries);

        restAudioSeriesMockMvc.perform(put("/api/audio-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioSeriesDTO)))
            .andExpect(status().isOk());

        // Validate the AudioSeries in the database
        List<AudioSeries> audioSeriesList = audioSeriesRepository.findAll();
        assertThat(audioSeriesList).hasSize(databaseSizeBeforeUpdate);
        AudioSeries testAudioSeries = audioSeriesList.get(audioSeriesList.size() - 1);
        assertThat(testAudioSeries.getSeriesName()).isEqualTo(UPDATED_SERIES_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAudioSeries() throws Exception {
        int databaseSizeBeforeUpdate = audioSeriesRepository.findAll().size();

        // Create the AudioSeries
        AudioSeriesDTO audioSeriesDTO = audioSeriesMapper.toDto(audioSeries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioSeriesMockMvc.perform(put("/api/audio-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioSeriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioSeries in the database
        List<AudioSeries> audioSeriesList = audioSeriesRepository.findAll();
        assertThat(audioSeriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAudioSeries() throws Exception {
        // Initialize the database
        audioSeriesRepository.saveAndFlush(audioSeries);

        int databaseSizeBeforeDelete = audioSeriesRepository.findAll().size();

        // Get the audioSeries
        restAudioSeriesMockMvc.perform(delete("/api/audio-series/{id}", audioSeries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AudioSeries> audioSeriesList = audioSeriesRepository.findAll();
        assertThat(audioSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioSeries.class);
        AudioSeries audioSeries1 = new AudioSeries();
        audioSeries1.setId(1L);
        AudioSeries audioSeries2 = new AudioSeries();
        audioSeries2.setId(audioSeries1.getId());
        assertThat(audioSeries1).isEqualTo(audioSeries2);
        audioSeries2.setId(2L);
        assertThat(audioSeries1).isNotEqualTo(audioSeries2);
        audioSeries1.setId(null);
        assertThat(audioSeries1).isNotEqualTo(audioSeries2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioSeriesDTO.class);
        AudioSeriesDTO audioSeriesDTO1 = new AudioSeriesDTO();
        audioSeriesDTO1.setId(1L);
        AudioSeriesDTO audioSeriesDTO2 = new AudioSeriesDTO();
        assertThat(audioSeriesDTO1).isNotEqualTo(audioSeriesDTO2);
        audioSeriesDTO2.setId(audioSeriesDTO1.getId());
        assertThat(audioSeriesDTO1).isEqualTo(audioSeriesDTO2);
        audioSeriesDTO2.setId(2L);
        assertThat(audioSeriesDTO1).isNotEqualTo(audioSeriesDTO2);
        audioSeriesDTO1.setId(null);
        assertThat(audioSeriesDTO1).isNotEqualTo(audioSeriesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(audioSeriesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(audioSeriesMapper.fromId(null)).isNull();
    }
}
