package be.jeffreyvdb.weddingsite.web.rest;

import be.jeffreyvdb.weddingsite.WeddingsiteApp;
import be.jeffreyvdb.weddingsite.domain.Accesscode;
import be.jeffreyvdb.weddingsite.repository.AccesscodeRepository;
import be.jeffreyvdb.weddingsite.service.AccesscodeService;
import be.jeffreyvdb.weddingsite.service.FamilyService;
import be.jeffreyvdb.weddingsite.service.PersonService;
import be.jeffreyvdb.weddingsite.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static be.jeffreyvdb.weddingsite.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccesscodeResource REST controller.
 *
 * @see AccesscodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeddingsiteApp.class)
public class AccesscodeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WILL_ATTEND = false;
    private static final Boolean UPDATED_WILL_ATTEND = true;

    @Autowired
    private AccesscodeRepository accesscodeRepository;

    @Mock
    private AccesscodeRepository accesscodeRepositoryMock;

    @Mock
    private AccesscodeService accesscodeServiceMock;

    @Autowired
    private AccesscodeService accesscodeService;

    @Mock
    private PersonService personServiceMock;

    @Autowired
    private PersonService personService;

    @Mock
    private FamilyService familyServiceMock;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccesscodeMockMvc;

    private Accesscode accesscode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccesscodeResource accesscodeResource = new AccesscodeResource(accesscodeService, personService, familyService);
        this.restAccesscodeMockMvc = MockMvcBuilders.standaloneSetup(accesscodeResource)
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
    public static Accesscode createEntity(EntityManager em) {
        Accesscode accesscode = new Accesscode()
            .code(DEFAULT_CODE)
            .willAttend(DEFAULT_WILL_ATTEND);
        return accesscode;
    }

    @Before
    public void initTest() {
        accesscode = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccesscode() throws Exception {
        int databaseSizeBeforeCreate = accesscodeRepository.findAll().size();

        // Create the Accesscode
        restAccesscodeMockMvc.perform(post("/api/accesscodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accesscode)))
            .andExpect(status().isCreated());

        // Validate the Accesscode in the database
        List<Accesscode> accesscodeList = accesscodeRepository.findAll();
        assertThat(accesscodeList).hasSize(databaseSizeBeforeCreate + 1);
        Accesscode testAccesscode = accesscodeList.get(accesscodeList.size() - 1);
        assertThat(testAccesscode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAccesscode.isWillAttend()).isEqualTo(DEFAULT_WILL_ATTEND);
    }

    @Test
    @Transactional
    public void createAccesscodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accesscodeRepository.findAll().size();

        // Create the Accesscode with an existing ID
        accesscode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccesscodeMockMvc.perform(post("/api/accesscodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accesscode)))
            .andExpect(status().isBadRequest());

        // Validate the Accesscode in the database
        List<Accesscode> accesscodeList = accesscodeRepository.findAll();
        assertThat(accesscodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accesscodeRepository.findAll().size();
        // set the field null
        accesscode.setCode(null);

        // Create the Accesscode, which fails.

        restAccesscodeMockMvc.perform(post("/api/accesscodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accesscode)))
            .andExpect(status().isBadRequest());

        List<Accesscode> accesscodeList = accesscodeRepository.findAll();
        assertThat(accesscodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccesscodes() throws Exception {
        // Initialize the database
        accesscodeRepository.saveAndFlush(accesscode);

        // Get all the accesscodeList
        restAccesscodeMockMvc.perform(get("/api/accesscodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accesscode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].willAttend").value(hasItem(DEFAULT_WILL_ATTEND.booleanValue())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAccesscodesWithEagerRelationshipsIsEnabled() throws Exception {
        AccesscodeResource accesscodeResource = new AccesscodeResource(accesscodeServiceMock, personServiceMock, familyServiceMock);
        when(accesscodeServiceMock.findAllWithEagerRelationships()).thenReturn((List<Accesscode>) new PageImpl(new ArrayList<>()));

        MockMvc restAccesscodeMockMvc = MockMvcBuilders.standaloneSetup(accesscodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAccesscodeMockMvc.perform(get("/api/accesscodes?eagerload=true"))
            .andExpect(status().isOk());

        verify(accesscodeServiceMock, times(1)).findAllWithEagerRelationships();
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAccesscodesWithEagerRelationshipsIsNotEnabled() throws Exception {
        AccesscodeResource accesscodeResource = new AccesscodeResource(accesscodeServiceMock, personServiceMock, familyServiceMock);
        when(accesscodeServiceMock.findAllWithEagerRelationships()).thenReturn((List<Accesscode>) new PageImpl(new ArrayList<>()));
        MockMvc restAccesscodeMockMvc = MockMvcBuilders.standaloneSetup(accesscodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAccesscodeMockMvc.perform(get("/api/accesscodes?eagerload=true"))
            .andExpect(status().isOk());

        verify(accesscodeServiceMock, times(1)).findAllWithEagerRelationships();
    }

    @Test
    @Transactional
    public void getAccesscode() throws Exception {
        // Initialize the database
        accesscodeRepository.saveAndFlush(accesscode);

        // Get the accesscode
        restAccesscodeMockMvc.perform(get("/api/accesscodes/{id}", accesscode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accesscode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.willAttend").value(DEFAULT_WILL_ATTEND.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccesscode() throws Exception {
        // Get the accesscode
        restAccesscodeMockMvc.perform(get("/api/accesscodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccesscode() throws Exception {
        // Initialize the database
        accesscodeService.save(accesscode);

        int databaseSizeBeforeUpdate = accesscodeRepository.findAll().size();

        // Update the accesscode
        Accesscode updatedAccesscode = accesscodeRepository.findById(accesscode.getId()).get();
        // Disconnect from session so that the updates on updatedAccesscode are not directly saved in db
        em.detach(updatedAccesscode);
        updatedAccesscode
            .code(UPDATED_CODE)
            .willAttend(UPDATED_WILL_ATTEND);

        restAccesscodeMockMvc.perform(put("/api/accesscodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccesscode)))
            .andExpect(status().isOk());

        // Validate the Accesscode in the database
        List<Accesscode> accesscodeList = accesscodeRepository.findAll();
        assertThat(accesscodeList).hasSize(databaseSizeBeforeUpdate);
        Accesscode testAccesscode = accesscodeList.get(accesscodeList.size() - 1);
        assertThat(testAccesscode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAccesscode.isWillAttend()).isEqualTo(UPDATED_WILL_ATTEND);
    }

    @Test
    @Transactional
    public void updateNonExistingAccesscode() throws Exception {
        int databaseSizeBeforeUpdate = accesscodeRepository.findAll().size();

        // Create the Accesscode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccesscodeMockMvc.perform(put("/api/accesscodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accesscode)))
            .andExpect(status().isBadRequest());

        // Validate the Accesscode in the database
        List<Accesscode> accesscodeList = accesscodeRepository.findAll();
        assertThat(accesscodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccesscode() throws Exception {
        // Initialize the database
        accesscodeService.save(accesscode);

        int databaseSizeBeforeDelete = accesscodeRepository.findAll().size();

        // Get the accesscode
        restAccesscodeMockMvc.perform(delete("/api/accesscodes/{id}", accesscode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Accesscode> accesscodeList = accesscodeRepository.findAll();
        assertThat(accesscodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accesscode.class);
        Accesscode accesscode1 = new Accesscode();
        accesscode1.setId(1L);
        Accesscode accesscode2 = new Accesscode();
        accesscode2.setId(accesscode1.getId());
        assertThat(accesscode1).isEqualTo(accesscode2);
        accesscode2.setId(2L);
        assertThat(accesscode1).isNotEqualTo(accesscode2);
        accesscode1.setId(null);
        assertThat(accesscode1).isNotEqualTo(accesscode2);
    }
}
