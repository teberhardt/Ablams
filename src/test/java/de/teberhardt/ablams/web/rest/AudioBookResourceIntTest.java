package de.teberhardt.ablams.web.rest;

import de.teberhardt.ablams.AblamsApp;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.repository.AudioBookRepository;
import de.teberhardt.ablams.service.AudioBookService;
import de.teberhardt.ablams.service.dto.AudioBookDTO;
import de.teberhardt.ablams.service.mapper.AudioBookMapper;
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

import de.teberhardt.ablams.domain.enumeration.Language;
/**
 * Test class for the AudioBookResource REST controller.
 *
 * @see AudioBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AblamsApp.class)
public class AudioBookResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.GERMAN;
    private static final Language UPDATED_LANGUAGE = Language.FRENCH;

    @Autowired
    private AudioBookRepository audioBookRepository;

    @Autowired
    private AudioBookMapper audioBookMapper;

    @Autowired
    private AudioBookService audioBookService;

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

    private MockMvc restAudioBookMockMvc;

    private AudioBook audioBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AudioBookResource audioBookResource = new AudioBookResource(audioBookService);
        this.restAudioBookMockMvc = MockMvcBuilders.standaloneSetup(audioBookResource)
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
    public static AudioBook createEntity(EntityManager em) {
        AudioBook audioBook = new AudioBook()
            .name(DEFAULT_NAME)
            .language(DEFAULT_LANGUAGE);
        return audioBook;
    }

    @Before
    public void initTest() {
        audioBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudioBook() throws Exception {
        int databaseSizeBeforeCreate = audioBookRepository.findAll().size();

        // Create the AudioBook
        AudioBookDTO audioBookDTO = audioBookMapper.toDto(audioBook);
        restAudioBookMockMvc.perform(post("/api/audio-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioBookDTO)))
            .andExpect(status().isCreated());

        // Validate the AudioBook in the database
        List<AudioBook> audioBookList = audioBookRepository.findAll();
        assertThat(audioBookList).hasSize(databaseSizeBeforeCreate + 1);
        AudioBook testAudioBook = audioBookList.get(audioBookList.size() - 1);
        assertThat(testAudioBook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAudioBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createAudioBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioBookRepository.findAll().size();

        // Create the AudioBook with an existing ID
        audioBook.setId(1L);
        AudioBookDTO audioBookDTO = audioBookMapper.toDto(audioBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioBookMockMvc.perform(post("/api/audio-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioBook in the database
        List<AudioBook> audioBookList = audioBookRepository.findAll();
        assertThat(audioBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudioBooks() throws Exception {
        // Initialize the database
        audioBookRepository.saveAndFlush(audioBook);

        // Get all the audioBookList
        restAudioBookMockMvc.perform(get("/api/audio-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audioBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getAudioBook() throws Exception {
        // Initialize the database
        audioBookRepository.saveAndFlush(audioBook);

        // Get the audioBook
        restAudioBookMockMvc.perform(get("/api/audio-books/{id}", audioBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audioBook.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudioBook() throws Exception {
        // Get the audioBook
        restAudioBookMockMvc.perform(get("/api/audio-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudioBook() throws Exception {
        // Initialize the database
        audioBookRepository.saveAndFlush(audioBook);

        int databaseSizeBeforeUpdate = audioBookRepository.findAll().size();

        // Update the audioBook
        AudioBook updatedAudioBook = audioBookRepository.findById(audioBook.getId()).get();
        // Disconnect from session so that the updates on updatedAudioBook are not directly saved in db
        em.detach(updatedAudioBook);
        updatedAudioBook
            .name(UPDATED_NAME)
            .language(UPDATED_LANGUAGE);
        AudioBookDTO audioBookDTO = audioBookMapper.toDto(updatedAudioBook);

        restAudioBookMockMvc.perform(put("/api/audio-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioBookDTO)))
            .andExpect(status().isOk());

        // Validate the AudioBook in the database
        List<AudioBook> audioBookList = audioBookRepository.findAll();
        assertThat(audioBookList).hasSize(databaseSizeBeforeUpdate);
        AudioBook testAudioBook = audioBookList.get(audioBookList.size() - 1);
        assertThat(testAudioBook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAudioBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingAudioBook() throws Exception {
        int databaseSizeBeforeUpdate = audioBookRepository.findAll().size();

        // Create the AudioBook
        AudioBookDTO audioBookDTO = audioBookMapper.toDto(audioBook);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioBookMockMvc.perform(put("/api/audio-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioBook in the database
        List<AudioBook> audioBookList = audioBookRepository.findAll();
        assertThat(audioBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAudioBook() throws Exception {
        // Initialize the database
        audioBookRepository.saveAndFlush(audioBook);

        int databaseSizeBeforeDelete = audioBookRepository.findAll().size();

        // Get the audioBook
        restAudioBookMockMvc.perform(delete("/api/audio-books/{id}", audioBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AudioBook> audioBookList = audioBookRepository.findAll();
        assertThat(audioBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioBook.class);
        AudioBook audioBook1 = new AudioBook();
        audioBook1.setId(1L);
        AudioBook audioBook2 = new AudioBook();
        audioBook2.setId(audioBook1.getId());
        assertThat(audioBook1).isEqualTo(audioBook2);
        audioBook2.setId(2L);
        assertThat(audioBook1).isNotEqualTo(audioBook2);
        audioBook1.setId(null);
        assertThat(audioBook1).isNotEqualTo(audioBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioBookDTO.class);
        AudioBookDTO audioBookDTO1 = new AudioBookDTO();
        audioBookDTO1.setId(1L);
        AudioBookDTO audioBookDTO2 = new AudioBookDTO();
        assertThat(audioBookDTO1).isNotEqualTo(audioBookDTO2);
        audioBookDTO2.setId(audioBookDTO1.getId());
        assertThat(audioBookDTO1).isEqualTo(audioBookDTO2);
        audioBookDTO2.setId(2L);
        assertThat(audioBookDTO1).isNotEqualTo(audioBookDTO2);
        audioBookDTO1.setId(null);
        assertThat(audioBookDTO1).isNotEqualTo(audioBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(audioBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(audioBookMapper.fromId(null)).isNull();
    }
}
