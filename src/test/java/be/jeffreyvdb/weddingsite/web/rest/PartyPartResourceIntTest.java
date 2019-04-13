package be.jeffreyvdb.weddingsite.web.rest;

import be.jeffreyvdb.weddingsite.WeddingsiteApp;

import be.jeffreyvdb.weddingsite.domain.PartyPart;
import be.jeffreyvdb.weddingsite.repository.PartyPartRepository;
import be.jeffreyvdb.weddingsite.service.PartyPartService;
import be.jeffreyvdb.weddingsite.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;


import static be.jeffreyvdb.weddingsite.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartyPartResource REST controller.
 *
 * @see PartyPartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingsiteApp.class)
public class PartyPartResourceIntTest {

    private static final String DEFAULT_PART_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PART_NAME = "BBBBBBBBBB";

    @Autowired
    private PartyPartRepository partyPartRepository;

    @Autowired
    private PartyPartService partyPartService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartyPartMockMvc;

    private PartyPart partyPart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyPartResource partyPartResource = new PartyPartResource(partyPartService);
        this.restPartyPartMockMvc = MockMvcBuilders.standaloneSetup(partyPartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyPart createEntity(EntityManager em) {
        PartyPart partyPart = new PartyPart()
            .partName(DEFAULT_PART_NAME);
        return partyPart;
    }

    @Before
    public void initTest() {
        partyPart = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyPart() throws Exception {
        int databaseSizeBeforeCreate = partyPartRepository.findAll().size();

        // Create the PartyPart
        restPartyPartMockMvc.perform(post("/api/party-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyPart)))
            .andExpect(status().isCreated());

        // Validate the PartyPart in the database
        List<PartyPart> partyPartList = partyPartRepository.findAll();
        assertThat(partyPartList).hasSize(databaseSizeBeforeCreate + 1);
        PartyPart testPartyPart = partyPartList.get(partyPartList.size() - 1);
        assertThat(testPartyPart.getPartName()).isEqualTo(DEFAULT_PART_NAME);
    }

    @Test
    @Transactional
    public void createPartyPartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyPartRepository.findAll().size();

        // Create the PartyPart with an existing ID
        partyPart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyPartMockMvc.perform(post("/api/party-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyPart)))
            .andExpect(status().isBadRequest());

        // Validate the PartyPart in the database
        List<PartyPart> partyPartList = partyPartRepository.findAll();
        assertThat(partyPartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPartyParts() throws Exception {
        // Initialize the database
        partyPartRepository.saveAndFlush(partyPart);

        // Get all the partyPartList
        restPartyPartMockMvc.perform(get("/api/party-parts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyPart.getId().intValue())))
            .andExpect(jsonPath("$.[*].partName").value(hasItem(DEFAULT_PART_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPartyPart() throws Exception {
        // Initialize the database
        partyPartRepository.saveAndFlush(partyPart);

        // Get the partyPart
        restPartyPartMockMvc.perform(get("/api/party-parts/{id}", partyPart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyPart.getId().intValue()))
            .andExpect(jsonPath("$.partName").value(DEFAULT_PART_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartyPart() throws Exception {
        // Get the partyPart
        restPartyPartMockMvc.perform(get("/api/party-parts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyPart() throws Exception {
        // Initialize the database
        partyPartService.save(partyPart);

        int databaseSizeBeforeUpdate = partyPartRepository.findAll().size();

        // Update the partyPart
        PartyPart updatedPartyPart = partyPartRepository.findById(partyPart.getId()).get();
        // Disconnect from session so that the updates on updatedPartyPart are not directly saved in db
        em.detach(updatedPartyPart);
        updatedPartyPart
            .partName(UPDATED_PART_NAME);

        restPartyPartMockMvc.perform(put("/api/party-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyPart)))
            .andExpect(status().isOk());

        // Validate the PartyPart in the database
        List<PartyPart> partyPartList = partyPartRepository.findAll();
        assertThat(partyPartList).hasSize(databaseSizeBeforeUpdate);
        PartyPart testPartyPart = partyPartList.get(partyPartList.size() - 1);
        assertThat(testPartyPart.getPartName()).isEqualTo(UPDATED_PART_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyPart() throws Exception {
        int databaseSizeBeforeUpdate = partyPartRepository.findAll().size();

        // Create the PartyPart

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyPartMockMvc.perform(put("/api/party-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyPart)))
            .andExpect(status().isBadRequest());

        // Validate the PartyPart in the database
        List<PartyPart> partyPartList = partyPartRepository.findAll();
        assertThat(partyPartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyPart() throws Exception {
        // Initialize the database
        partyPartService.save(partyPart);

        int databaseSizeBeforeDelete = partyPartRepository.findAll().size();

        // Get the partyPart
        restPartyPartMockMvc.perform(delete("/api/party-parts/{id}", partyPart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartyPart> partyPartList = partyPartRepository.findAll();
        assertThat(partyPartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyPart.class);
        PartyPart partyPart1 = new PartyPart();
        partyPart1.setId(1L);
        PartyPart partyPart2 = new PartyPart();
        partyPart2.setId(partyPart1.getId());
        assertThat(partyPart1).isEqualTo(partyPart2);
        partyPart2.setId(2L);
        assertThat(partyPart1).isNotEqualTo(partyPart2);
        partyPart1.setId(null);
        assertThat(partyPart1).isNotEqualTo(partyPart2);
    }
}
