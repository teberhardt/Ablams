package de.teberhardt.ablams.web.rest;

import de.teberhardt.ablams.AblamsApp;

import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.repository.AudioLibraryRepository;
import de.teberhardt.ablams.service.AudioLibraryService;
import de.teberhardt.ablams.service.dto.AudioLibraryDTO;
import de.teberhardt.ablams.service.mapper.AudioLibraryMapper;
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
 * Test class for the AudioLibraryResource REST controller.
 *
 * @see AudioLibraryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AblamsApp.class)
public class AudioLibraryResourceIntTest {

    private static final String DEFAULT_FILEPATH = "AAAAAAAAAA";
    private static final String UPDATED_FILEPATH = "BBBBBBBBBB";

    @Autowired
    private AudioLibraryRepository audioLibraryRepository;

    @Autowired
    private AudioLibraryMapper audioLibraryMapper;

    @Autowired
    private AudioLibraryService audioLibraryService;

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

    private MockMvc restAudioLibraryMockMvc;

    private AudioLibrary audioLibrary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AudioLibraryResource audioLibraryResource = new AudioLibraryResource(audioLibraryService);
        this.restAudioLibraryMockMvc = MockMvcBuilders.standaloneSetup(audioLibraryResource)
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
    public static AudioLibrary createEntity(EntityManager em) {
        AudioLibrary audioLibrary = new AudioLibrary()
            .filepath(DEFAULT_FILEPATH);
        return audioLibrary;
    }

    @Before
    public void initTest() {
        audioLibrary = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudioLibrary() throws Exception {
        int databaseSizeBeforeCreate = audioLibraryRepository.findAll().size();

        // Create the AudioLibrary
        AudioLibraryDTO audioLibraryDTO = audioLibraryMapper.toDto(audioLibrary);
        restAudioLibraryMockMvc.perform(post("/api/audio-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioLibraryDTO)))
            .andExpect(status().isCreated());

        // Validate the AudioLibrary in the database
        List<AudioLibrary> audioLibraryList = audioLibraryRepository.findAll();
        assertThat(audioLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        AudioLibrary testAudioLibrary = audioLibraryList.get(audioLibraryList.size() - 1);
        assertThat(testAudioLibrary.getFilepath()).isEqualTo(DEFAULT_FILEPATH);
    }

    @Test
    @Transactional
    public void createAudioLibraryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioLibraryRepository.findAll().size();

        // Create the AudioLibrary with an existing ID
        audioLibrary.setId(1L);
        AudioLibraryDTO audioLibraryDTO = audioLibraryMapper.toDto(audioLibrary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioLibraryMockMvc.perform(post("/api/audio-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioLibraryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioLibrary in the database
        List<AudioLibrary> audioLibraryList = audioLibraryRepository.findAll();
        assertThat(audioLibraryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudioLibraries() throws Exception {
        // Initialize the database
        audioLibraryRepository.saveAndFlush(audioLibrary);

        // Get all the audioLibraryList
        restAudioLibraryMockMvc.perform(get("/api/audio-libraries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audioLibrary.getId().intValue())))
            .andExpect(jsonPath("$.[*].filepath").value(hasItem(DEFAULT_FILEPATH.toString())));
    }
    
    @Test
    @Transactional
    public void getAudioLibrary() throws Exception {
        // Initialize the database
        audioLibraryRepository.saveAndFlush(audioLibrary);

        // Get the audioLibrary
        restAudioLibraryMockMvc.perform(get("/api/audio-libraries/{id}", audioLibrary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audioLibrary.getId().intValue()))
            .andExpect(jsonPath("$.filepath").value(DEFAULT_FILEPATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudioLibrary() throws Exception {
        // Get the audioLibrary
        restAudioLibraryMockMvc.perform(get("/api/audio-libraries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudioLibrary() throws Exception {
        // Initialize the database
        audioLibraryRepository.saveAndFlush(audioLibrary);

        int databaseSizeBeforeUpdate = audioLibraryRepository.findAll().size();

        // Update the audioLibrary
        AudioLibrary updatedAudioLibrary = audioLibraryRepository.findById(audioLibrary.getId()).get();
        // Disconnect from session so that the updates on updatedAudioLibrary are not directly saved in db
        em.detach(updatedAudioLibrary);
        updatedAudioLibrary
            .filepath(UPDATED_FILEPATH);
        AudioLibraryDTO audioLibraryDTO = audioLibraryMapper.toDto(updatedAudioLibrary);

        restAudioLibraryMockMvc.perform(put("/api/audio-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioLibraryDTO)))
            .andExpect(status().isOk());

        // Validate the AudioLibrary in the database
        List<AudioLibrary> audioLibraryList = audioLibraryRepository.findAll();
        assertThat(audioLibraryList).hasSize(databaseSizeBeforeUpdate);
        AudioLibrary testAudioLibrary = audioLibraryList.get(audioLibraryList.size() - 1);
        assertThat(testAudioLibrary.getFilepath()).isEqualTo(UPDATED_FILEPATH);
    }

    @Test
    @Transactional
    public void updateNonExistingAudioLibrary() throws Exception {
        int databaseSizeBeforeUpdate = audioLibraryRepository.findAll().size();

        // Create the AudioLibrary
        AudioLibraryDTO audioLibraryDTO = audioLibraryMapper.toDto(audioLibrary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioLibraryMockMvc.perform(put("/api/audio-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioLibraryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioLibrary in the database
        List<AudioLibrary> audioLibraryList = audioLibraryRepository.findAll();
        assertThat(audioLibraryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAudioLibrary() throws Exception {
        // Initialize the database
        audioLibraryRepository.saveAndFlush(audioLibrary);

        int databaseSizeBeforeDelete = audioLibraryRepository.findAll().size();

        // Get the audioLibrary
        restAudioLibraryMockMvc.perform(delete("/api/audio-libraries/{id}", audioLibrary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AudioLibrary> audioLibraryList = audioLibraryRepository.findAll();
        assertThat(audioLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioLibrary.class);
        AudioLibrary audioLibrary1 = new AudioLibrary();
        audioLibrary1.setId(1L);
        AudioLibrary audioLibrary2 = new AudioLibrary();
        audioLibrary2.setId(audioLibrary1.getId());
        assertThat(audioLibrary1).isEqualTo(audioLibrary2);
        audioLibrary2.setId(2L);
        assertThat(audioLibrary1).isNotEqualTo(audioLibrary2);
        audioLibrary1.setId(null);
        assertThat(audioLibrary1).isNotEqualTo(audioLibrary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioLibraryDTO.class);
        AudioLibraryDTO audioLibraryDTO1 = new AudioLibraryDTO();
        audioLibraryDTO1.setId(1L);
        AudioLibraryDTO audioLibraryDTO2 = new AudioLibraryDTO();
        assertThat(audioLibraryDTO1).isNotEqualTo(audioLibraryDTO2);
        audioLibraryDTO2.setId(audioLibraryDTO1.getId());
        assertThat(audioLibraryDTO1).isEqualTo(audioLibraryDTO2);
        audioLibraryDTO2.setId(2L);
        assertThat(audioLibraryDTO1).isNotEqualTo(audioLibraryDTO2);
        audioLibraryDTO1.setId(null);
        assertThat(audioLibraryDTO1).isNotEqualTo(audioLibraryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(audioLibraryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(audioLibraryMapper.fromId(null)).isNull();
    }
}
