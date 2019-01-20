package de.teberhardt.ablams.web.rest;

import de.teberhardt.ablams.AblamsApp;

import de.teberhardt.ablams.domain.BookSeries;
import de.teberhardt.ablams.repository.BookSeriesRepository;
import de.teberhardt.ablams.service.BookSeriesService;
import de.teberhardt.ablams.service.dto.BookSeriesDTO;
import de.teberhardt.ablams.service.mapper.BookSeriesMapper;
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
 * Test class for the BookSeriesResource REST controller.
 *
 * @see BookSeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AblamsApp.class)
public class BookSeriesResourceIntTest {

    private static final String DEFAULT_SERIES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERIES_NAME = "BBBBBBBBBB";

    @Autowired
    private BookSeriesRepository bookSeriesRepository;

    @Autowired
    private BookSeriesMapper bookSeriesMapper;

    @Autowired
    private BookSeriesService bookSeriesService;

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

    private MockMvc restBookSeriesMockMvc;

    private BookSeries bookSeries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookSeriesResource bookSeriesResource = new BookSeriesResource(bookSeriesService);
        this.restBookSeriesMockMvc = MockMvcBuilders.standaloneSetup(bookSeriesResource)
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
    public static BookSeries createEntity(EntityManager em) {
        BookSeries bookSeries = new BookSeries()
            .seriesName(DEFAULT_SERIES_NAME);
        return bookSeries;
    }

    @Before
    public void initTest() {
        bookSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookSeries() throws Exception {
        int databaseSizeBeforeCreate = bookSeriesRepository.findAll().size();

        // Create the BookSeries
        BookSeriesDTO bookSeriesDTO = bookSeriesMapper.toDto(bookSeries);
        restBookSeriesMockMvc.perform(post("/api/book-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSeriesDTO)))
            .andExpect(status().isCreated());

        // Validate the BookSeries in the database
        List<BookSeries> bookSeriesList = bookSeriesRepository.findAll();
        assertThat(bookSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        BookSeries testBookSeries = bookSeriesList.get(bookSeriesList.size() - 1);
        assertThat(testBookSeries.getSeriesName()).isEqualTo(DEFAULT_SERIES_NAME);
    }

    @Test
    @Transactional
    public void createBookSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookSeriesRepository.findAll().size();

        // Create the BookSeries with an existing ID
        bookSeries.setId(1L);
        BookSeriesDTO bookSeriesDTO = bookSeriesMapper.toDto(bookSeries);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookSeriesMockMvc.perform(post("/api/book-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSeriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookSeries in the database
        List<BookSeries> bookSeriesList = bookSeriesRepository.findAll();
        assertThat(bookSeriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookSeries() throws Exception {
        // Initialize the database
        bookSeriesRepository.saveAndFlush(bookSeries);

        // Get all the bookSeriesList
        restBookSeriesMockMvc.perform(get("/api/book-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].seriesName").value(hasItem(DEFAULT_SERIES_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getBookSeries() throws Exception {
        // Initialize the database
        bookSeriesRepository.saveAndFlush(bookSeries);

        // Get the bookSeries
        restBookSeriesMockMvc.perform(get("/api/book-series/{id}", bookSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookSeries.getId().intValue()))
            .andExpect(jsonPath("$.seriesName").value(DEFAULT_SERIES_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookSeries() throws Exception {
        // Get the bookSeries
        restBookSeriesMockMvc.perform(get("/api/book-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookSeries() throws Exception {
        // Initialize the database
        bookSeriesRepository.saveAndFlush(bookSeries);

        int databaseSizeBeforeUpdate = bookSeriesRepository.findAll().size();

        // Update the bookSeries
        BookSeries updatedBookSeries = bookSeriesRepository.findById(bookSeries.getId()).get();
        // Disconnect from session so that the updates on updatedBookSeries are not directly saved in db
        em.detach(updatedBookSeries);
        updatedBookSeries
            .seriesName(UPDATED_SERIES_NAME);
        BookSeriesDTO bookSeriesDTO = bookSeriesMapper.toDto(updatedBookSeries);

        restBookSeriesMockMvc.perform(put("/api/book-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSeriesDTO)))
            .andExpect(status().isOk());

        // Validate the BookSeries in the database
        List<BookSeries> bookSeriesList = bookSeriesRepository.findAll();
        assertThat(bookSeriesList).hasSize(databaseSizeBeforeUpdate);
        BookSeries testBookSeries = bookSeriesList.get(bookSeriesList.size() - 1);
        assertThat(testBookSeries.getSeriesName()).isEqualTo(UPDATED_SERIES_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBookSeries() throws Exception {
        int databaseSizeBeforeUpdate = bookSeriesRepository.findAll().size();

        // Create the BookSeries
        BookSeriesDTO bookSeriesDTO = bookSeriesMapper.toDto(bookSeries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookSeriesMockMvc.perform(put("/api/book-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSeriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookSeries in the database
        List<BookSeries> bookSeriesList = bookSeriesRepository.findAll();
        assertThat(bookSeriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookSeries() throws Exception {
        // Initialize the database
        bookSeriesRepository.saveAndFlush(bookSeries);

        int databaseSizeBeforeDelete = bookSeriesRepository.findAll().size();

        // Get the bookSeries
        restBookSeriesMockMvc.perform(delete("/api/book-series/{id}", bookSeries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookSeries> bookSeriesList = bookSeriesRepository.findAll();
        assertThat(bookSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookSeries.class);
        BookSeries bookSeries1 = new BookSeries();
        bookSeries1.setId(1L);
        BookSeries bookSeries2 = new BookSeries();
        bookSeries2.setId(bookSeries1.getId());
        assertThat(bookSeries1).isEqualTo(bookSeries2);
        bookSeries2.setId(2L);
        assertThat(bookSeries1).isNotEqualTo(bookSeries2);
        bookSeries1.setId(null);
        assertThat(bookSeries1).isNotEqualTo(bookSeries2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookSeriesDTO.class);
        BookSeriesDTO bookSeriesDTO1 = new BookSeriesDTO();
        bookSeriesDTO1.setId(1L);
        BookSeriesDTO bookSeriesDTO2 = new BookSeriesDTO();
        assertThat(bookSeriesDTO1).isNotEqualTo(bookSeriesDTO2);
        bookSeriesDTO2.setId(bookSeriesDTO1.getId());
        assertThat(bookSeriesDTO1).isEqualTo(bookSeriesDTO2);
        bookSeriesDTO2.setId(2L);
        assertThat(bookSeriesDTO1).isNotEqualTo(bookSeriesDTO2);
        bookSeriesDTO1.setId(null);
        assertThat(bookSeriesDTO1).isNotEqualTo(bookSeriesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookSeriesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookSeriesMapper.fromId(null)).isNull();
    }
}
