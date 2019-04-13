package be.jeffreyvdb.weddingsite.web.rest;

import be.jeffreyvdb.weddingsite.domain.Accesscode;
import be.jeffreyvdb.weddingsite.domain.Family;
import be.jeffreyvdb.weddingsite.domain.Person;
import be.jeffreyvdb.weddingsite.service.AccesscodeService;
import be.jeffreyvdb.weddingsite.service.FamilyService;
import be.jeffreyvdb.weddingsite.service.PersonService;
import be.jeffreyvdb.weddingsite.web.rest.errors.BadRequestAlertException;
import be.jeffreyvdb.weddingsite.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Accesscode.
 */
@RestController
@RequestMapping("/api")
public class AccesscodeResource {

    private final Logger log = LoggerFactory.getLogger(AccesscodeResource.class);

    private static final String ENTITY_NAME = "accesscode";

    private final AccesscodeService accesscodeService;
    private PersonService personService = null;
    private FamilyService familyService;


    public AccesscodeResource(AccesscodeService accesscodeService, PersonService personService, FamilyService familyService) {
        this.accesscodeService = accesscodeService;
        this.personService = personService;
        this.familyService = familyService;
    }

    /**
     * POST  /accesscodes : Create a new accesscode.
     *
     * @param accesscode the accesscode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accesscode, or with status 400 (Bad Request) if the accesscode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accesscodes")
    @Timed
    public ResponseEntity<Accesscode> createAccesscode(@Valid @RequestBody Accesscode accesscode) throws URISyntaxException {
        log.debug("REST request to save Accesscode : {}", accesscode);
        if (accesscode.getId() != null) {
            throw new BadRequestAlertException("A new accesscode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Accesscode result = accesscodeService.save(accesscode);
        return ResponseEntity.created(new URI("/api/accesscodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accesscodes : Updates an existing accesscode.
     *
     * @param accesscode the accesscode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accesscode,
     * or with status 400 (Bad Request) if the accesscode is not valid,
     * or with status 500 (Internal Server Error) if the accesscode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accesscodes")
    @Timed
    public ResponseEntity<Accesscode> updateAccesscode(@Valid @RequestBody Accesscode accesscode) throws URISyntaxException {
        log.debug("REST request to update Accesscode : {}", accesscode);
        if (accesscode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Accesscode result = accesscodeService.save(accesscode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accesscode.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accesscodes : Updates an existing accesscode.
     *
     * @param accesscode the accesscode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accesscode,
     * or with status 400 (Bad Request) if the accesscode is not valid,
     * or with status 500 (Internal Server Error) if the accesscode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accesscodes/form")
    @Timed
    public ResponseEntity<Accesscode> updateAccesscodeWithAccessCodeInParam(@Valid @RequestBody Accesscode accesscode) throws URISyntaxException {
        log.debug("REST request to update Accesscode : {}", accesscode);
        if (accesscode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Accesscode result = accesscodeService.save(accesscode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accesscode.getId().toString()))
            .body(result);
    }
    /**
     * GET  /accesscodes : get all the accesscodes.*
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of accesscodes in body
     */
    @GetMapping("/accesscodes")
    @Timed
    public ResponseEntity<List<Accesscode>> getAllAccesscodes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Accesscodes");
        List<Accesscode> page;
        if (eagerload) {
            page = accesscodeService.findAllWithEagerRelationships();
        } else {
            page = accesscodeService.findAll();
        }
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/accesscodes?eagerload=%b", eagerload));
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET  /accesscodes/:id : get the "id" accesscode.
     *
     * @param id the id of the accesscode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accesscode, or with status 404 (Not Found)
     */
    @GetMapping("/accesscodes/{id}")
    @Timed
    public ResponseEntity<Accesscode> getAccesscode(@PathVariable Long id) {
        log.debug("REST request to get Accesscode : {}", id);
        Optional<Accesscode> accesscode = accesscodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accesscode);
    }


    /**
     * GET  /accesscodes/:id : get the "id" accesscode.
     *
     * @param accesscode the id of the accesscode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accesscode, or with status 404 (Not Found)
     */
    @GetMapping("/accesscode/{accesscode}/family")
    @Timed
    public ResponseEntity<Accesscode> getAccesscodeByCode(@PathVariable String accesscode) {
        log.debug("REST request to get Accesscode : {}", accesscode);
        Optional<Accesscode> accesscodeOptional = accesscodeService.findOneByCode(accesscode);

        return ResponseUtil.wrapOrNotFound(accesscodeOptional);
    }

    /**
     * GET  /accesscodes/:id : get the "id" accesscode.
     *
     * @param accesscode the id of the accesscode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accesscode, or with status 404 (Not Found)
     */
    @GetMapping("/accesscode/{accesscode}/info")
    @Timed
    public ResponseEntity<List<Object>> getAllInfoByCode(@PathVariable String accesscode) {
        List<Object> familyInfo = new ArrayList<>();
        log.debug("REST request to get Accesscode : {}", accesscode);
        Optional<Accesscode> accesscodeOptional = accesscodeService.findOneByCode(accesscode);
        familyInfo.add(accesscodeOptional);

        Optional<Family> family = familyService.findOneByAccessCode(accesscode);
        if (family.isPresent()){

            List<Person> personList = personService.findAllPersonsFromOneFamily(family.get().getId());
            List<Optional<Person>> persons = new ArrayList<>();
            Iterator itr = personList.iterator();
            while (itr.hasNext()) {
                Person personId = (Person) itr.next();
//            Long personId = Long.parseLong(String.valueOf(obj[2]));
                System.out.println(personId);

                log.debug("REST request to get Person : {}", personId);
                Optional<Person> person = personService.findOne(personId.getId());
                System.out.println(person);
                persons.add(person);
            }
            familyInfo.add(persons);
        }
        return ResponseEntity.ok().body(familyInfo);
    }

    /**
     * DELETE  /accesscodes/:id : delete the "id" accesscode.
     *
     * @param id the id of the accesscode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accesscodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccesscode(@PathVariable Long id) {
        log.debug("REST request to delete Accesscode : {}", id);
        accesscodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
