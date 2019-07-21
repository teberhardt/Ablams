package de.teberhardt.ablams.web.rest;

import de.teberhardt.ablams.AblamsApp;

import de.teberhardt.ablams.domain.AudioFile;
import de.teberhardt.ablams.repository.AudioFileRepository;
import de.teberhardt.ablams.service.AudioFileService;
import de.teberhardt.ablams.service.dto.AudioFileDTO;
import de.teberhardt.ablams.service.mapper.AudioFileMapper;
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

import de.teberhardt.ablams.domain.enumeration.FileType;
/**
 * Test class for the AudioFileResource REST controller.
 *
 * @see AudioFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AblamsApp.class)
public class AudioFileResourceIntTest {

    private static final FileType DEFAULT_FILE_TYPE = FileType.MP3;
    private static final FileType UPDATED_FILE_TYPE = FileType.FLAC;

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    @Autowired
    private AudioFileRepository audioFileRepository;

    @Autowired
    private AudioFileMapper audioFileMapper;

    @Autowired
    private AudioFileService audioFileService;

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

    private MockMvc restAudioFileMockMvc;

    private AudioFile audioFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AudioFileResource audioFileResource = new AudioFileResource(audioFileService);
        this.restAudioFileMockMvc = MockMvcBuilders.standaloneSetup(audioFileResource)
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
    public static AudioFile createEntity(EntityManager em) {
        AudioFile audioFile = new AudioFile()
            .fileType(DEFAULT_FILE_TYPE)
            .filePath(DEFAULT_FILE_PATH);
        return audioFile;
    }

    @Before
    public void initTest() {
        audioFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudioFile() throws Exception {
        int databaseSizeBeforeCreate = audioFileRepository.findAll().size();

        // Create the AudioFile
        AudioFileDTO audioFileDTO = audioFileMapper.toDto(audioFile);
        restAudioFileMockMvc.perform(post("/api/audio-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioFileDTO)))
            .andExpect(status().isCreated());

        // Validate the AudioFile in the database
        List<AudioFile> audioFileList = audioFileRepository.findAll();
        assertThat(audioFileList).hasSize(databaseSizeBeforeCreate + 1);
        AudioFile testAudioFile = audioFileList.get(audioFileList.size() - 1);
        assertThat(testAudioFile.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testAudioFile.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
    }

    @Test
    @Transactional
    public void createAudioFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioFileRepository.findAll().size();

        // Create the AudioFile with an existing ID
        audioFile.setId(1L);
        AudioFileDTO audioFileDTO = audioFileMapper.toDto(audioFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioFileMockMvc.perform(post("/api/audio-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioFile in the database
        List<AudioFile> audioFileList = audioFileRepository.findAll();
        assertThat(audioFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudioFiles() throws Exception {
        // Initialize the database
        audioFileRepository.saveAndFlush(audioFile);

        // Get all the audioFileList
        restAudioFileMockMvc.perform(get("/api/audio-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audioFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH.toString())));
    }
    
    @Test
    @Transactional
    public void getAudioFile() throws Exception {
        // Initialize the database
        audioFileRepository.saveAndFlush(audioFile);

        // Get the audioFile
        restAudioFileMockMvc.perform(get("/api/audio-files/{id}", audioFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audioFile.getId().intValue()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudioFile() throws Exception {
        // Get the audioFile
        restAudioFileMockMvc.perform(get("/api/audio-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudioFile() throws Exception {
        // Initialize the database
        audioFileRepository.saveAndFlush(audioFile);

        int databaseSizeBeforeUpdate = audioFileRepository.findAll().size();

        // Update the audioFile
        AudioFile updatedAudioFile = audioFileRepository.findById(audioFile.getId()).get();
        // Disconnect from session so that the updates on updatedAudioFile are not directly saved in db
        em.detach(updatedAudioFile);
        updatedAudioFile
            .fileType(UPDATED_FILE_TYPE)
            .filePath(UPDATED_FILE_PATH);
        AudioFileDTO audioFileDTO = audioFileMapper.toDto(updatedAudioFile);

        restAudioFileMockMvc.perform(put("/api/audio-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioFileDTO)))
            .andExpect(status().isOk());

        // Validate the AudioFile in the database
        List<AudioFile> audioFileList = audioFileRepository.findAll();
        assertThat(audioFileList).hasSize(databaseSizeBeforeUpdate);
        AudioFile testAudioFile = audioFileList.get(audioFileList.size() - 1);
        assertThat(testAudioFile.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testAudioFile.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingAudioFile() throws Exception {
        int databaseSizeBeforeUpdate = audioFileRepository.findAll().size();

        // Create the AudioFile
        AudioFileDTO audioFileDTO = audioFileMapper.toDto(audioFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioFileMockMvc.perform(put("/api/audio-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AudioFile in the database
        List<AudioFile> audioFileList = audioFileRepository.findAll();
        assertThat(audioFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAudioFile() throws Exception {
        // Initialize the database
        audioFileRepository.saveAndFlush(audioFile);

        int databaseSizeBeforeDelete = audioFileRepository.findAll().size();

        // Get the audioFile
        restAudioFileMockMvc.perform(delete("/api/audio-files/{id}", audioFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AudioFile> audioFileList = audioFileRepository.findAll();
        assertThat(audioFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioFile.class);
        AudioFile audioFile1 = new AudioFile();
        audioFile1.setId(1L);
        AudioFile audioFile2 = new AudioFile();
        audioFile2.setId(audioFile1.getId());
        assertThat(audioFile1).isEqualTo(audioFile2);
        audioFile2.setId(2L);
        assertThat(audioFile1).isNotEqualTo(audioFile2);
        audioFile1.setId(null);
        assertThat(audioFile1).isNotEqualTo(audioFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioFileDTO.class);
        AudioFileDTO audioFileDTO1 = new AudioFileDTO();
        audioFileDTO1.setId(1L);
        AudioFileDTO audioFileDTO2 = new AudioFileDTO();
        assertThat(audioFileDTO1).isNotEqualTo(audioFileDTO2);
        audioFileDTO2.setId(audioFileDTO1.getId());
        assertThat(audioFileDTO1).isEqualTo(audioFileDTO2);
        audioFileDTO2.setId(2L);
        assertThat(audioFileDTO1).isNotEqualTo(audioFileDTO2);
        audioFileDTO1.setId(null);
        assertThat(audioFileDTO1).isNotEqualTo(audioFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(audioFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(audioFileMapper.fromId(null)).isNull();
    }
}
