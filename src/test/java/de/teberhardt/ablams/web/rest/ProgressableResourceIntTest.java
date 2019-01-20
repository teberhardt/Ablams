package de.teberhardt.ablams.web.rest;

import de.teberhardt.ablams.AblamsApp;

import de.teberhardt.ablams.domain.Progressable;
import de.teberhardt.ablams.repository.ProgressableRepository;
import de.teberhardt.ablams.service.ProgressableService;
import de.teberhardt.ablams.service.dto.ProgressableDTO;
import de.teberhardt.ablams.service.mapper.ProgressableMapper;
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
 * Test class for the ProgressableResource REST controller.
 *
 * @see ProgressableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AblamsApp.class)
public class ProgressableResourceIntTest {

    private static final Float DEFAULT_PROGRESS = 1F;
    private static final Float UPDATED_PROGRESS = 2F;

    private static final Float DEFAULT_DURATION = 1F;
    private static final Float UPDATED_DURATION = 2F;

    @Autowired
    private ProgressableRepository progressableRepository;

    @Autowired
    private ProgressableMapper progressableMapper;

    @Autowired
    private ProgressableService progressableService;

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

    private MockMvc restProgressableMockMvc;

    private Progressable progressable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgressableResource progressableResource = new ProgressableResource(progressableService);
        this.restProgressableMockMvc = MockMvcBuilders.standaloneSetup(progressableResource)
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
    public static Progressable createEntity(EntityManager em) {
        Progressable progressable = new Progressable()
            .progress(DEFAULT_PROGRESS)
            .duration(DEFAULT_DURATION);
        return progressable;
    }

    @Before
    public void initTest() {
        progressable = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgressable() throws Exception {
        int databaseSizeBeforeCreate = progressableRepository.findAll().size();

        // Create the Progressable
        ProgressableDTO progressableDTO = progressableMapper.toDto(progressable);
        restProgressableMockMvc.perform(post("/api/progressables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(progressableDTO)))
            .andExpect(status().isCreated());

        // Validate the Progressable in the database
        List<Progressable> progressableList = progressableRepository.findAll();
        assertThat(progressableList).hasSize(databaseSizeBeforeCreate + 1);
        Progressable testProgressable = progressableList.get(progressableList.size() - 1);
        assertThat(testProgressable.getProgress()).isEqualTo(DEFAULT_PROGRESS);
        assertThat(testProgressable.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createProgressableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = progressableRepository.findAll().size();

        // Create the Progressable with an existing ID
        progressable.setId(1L);
        ProgressableDTO progressableDTO = progressableMapper.toDto(progressable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgressableMockMvc.perform(post("/api/progressables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(progressableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Progressable in the database
        List<Progressable> progressableList = progressableRepository.findAll();
        assertThat(progressableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProgressables() throws Exception {
        // Initialize the database
        progressableRepository.saveAndFlush(progressable);

        // Get all the progressableList
        restProgressableMockMvc.perform(get("/api/progressables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(progressable.getId().intValue())))
            .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProgressable() throws Exception {
        // Initialize the database
        progressableRepository.saveAndFlush(progressable);

        // Get the progressable
        restProgressableMockMvc.perform(get("/api/progressables/{id}", progressable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(progressable.getId().intValue()))
            .andExpect(jsonPath("$.progress").value(DEFAULT_PROGRESS.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProgressable() throws Exception {
        // Get the progressable
        restProgressableMockMvc.perform(get("/api/progressables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgressable() throws Exception {
        // Initialize the database
        progressableRepository.saveAndFlush(progressable);

        int databaseSizeBeforeUpdate = progressableRepository.findAll().size();

        // Update the progressable
        Progressable updatedProgressable = progressableRepository.findById(progressable.getId()).get();
        // Disconnect from session so that the updates on updatedProgressable are not directly saved in db
        em.detach(updatedProgressable);
        updatedProgressable
            .progress(UPDATED_PROGRESS)
            .duration(UPDATED_DURATION);
        ProgressableDTO progressableDTO = progressableMapper.toDto(updatedProgressable);

        restProgressableMockMvc.perform(put("/api/progressables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(progressableDTO)))
            .andExpect(status().isOk());

        // Validate the Progressable in the database
        List<Progressable> progressableList = progressableRepository.findAll();
        assertThat(progressableList).hasSize(databaseSizeBeforeUpdate);
        Progressable testProgressable = progressableList.get(progressableList.size() - 1);
        assertThat(testProgressable.getProgress()).isEqualTo(UPDATED_PROGRESS);
        assertThat(testProgressable.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void updateNonExistingProgressable() throws Exception {
        int databaseSizeBeforeUpdate = progressableRepository.findAll().size();

        // Create the Progressable
        ProgressableDTO progressableDTO = progressableMapper.toDto(progressable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgressableMockMvc.perform(put("/api/progressables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(progressableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Progressable in the database
        List<Progressable> progressableList = progressableRepository.findAll();
        assertThat(progressableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgressable() throws Exception {
        // Initialize the database
        progressableRepository.saveAndFlush(progressable);

        int databaseSizeBeforeDelete = progressableRepository.findAll().size();

        // Get the progressable
        restProgressableMockMvc.perform(delete("/api/progressables/{id}", progressable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Progressable> progressableList = progressableRepository.findAll();
        assertThat(progressableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Progressable.class);
        Progressable progressable1 = new Progressable();
        progressable1.setId(1L);
        Progressable progressable2 = new Progressable();
        progressable2.setId(progressable1.getId());
        assertThat(progressable1).isEqualTo(progressable2);
        progressable2.setId(2L);
        assertThat(progressable1).isNotEqualTo(progressable2);
        progressable1.setId(null);
        assertThat(progressable1).isNotEqualTo(progressable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgressableDTO.class);
        ProgressableDTO progressableDTO1 = new ProgressableDTO();
        progressableDTO1.setId(1L);
        ProgressableDTO progressableDTO2 = new ProgressableDTO();
        assertThat(progressableDTO1).isNotEqualTo(progressableDTO2);
        progressableDTO2.setId(progressableDTO1.getId());
        assertThat(progressableDTO1).isEqualTo(progressableDTO2);
        progressableDTO2.setId(2L);
        assertThat(progressableDTO1).isNotEqualTo(progressableDTO2);
        progressableDTO1.setId(null);
        assertThat(progressableDTO1).isNotEqualTo(progressableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(progressableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(progressableMapper.fromId(null)).isNull();
    }
}
