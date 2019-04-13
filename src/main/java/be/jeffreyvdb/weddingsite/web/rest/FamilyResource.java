package be.jeffreyvdb.weddingsite.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.jeffreyvdb.weddingsite.domain.Family;
import be.jeffreyvdb.weddingsite.service.FamilyService;
import be.jeffreyvdb.weddingsite.web.rest.errors.BadRequestAlertException;
import be.jeffreyvdb.weddingsite.web.rest.util.HeaderUtil;
import be.jeffreyvdb.weddingsite.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Family.
 */
@RestController
@RequestMapping("/api")
public class FamilyResource {

    private final Logger log = LoggerFactory.getLogger(FamilyResource.class);

    private static final String ENTITY_NAME = "family";

    private final FamilyService familyService;

    public FamilyResource(FamilyService familyService) {
        this.familyService = familyService;
    }

    /**
     * POST  /families : Create a new family.
     *
     * @param family the family to create
     * @return the ResponseEntity with status 201 (Created) and with body the new family, or with status 400 (Bad Request) if the family has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/families")
    @Timed
    public ResponseEntity<Family> createFamily(@RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to save Family : {}", family);
        if (family.getId() != null) {
            throw new BadRequestAlertException("A new family cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Family result = familyService.save(family);
        return ResponseEntity.created(new URI("/api/families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /families : Updates an existing family.
     *
     * @param family the family to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated family,
     * or with status 400 (Bad Request) if the family is not valid,
     * or with status 500 (Internal Server Error) if the family couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/families")
    @Timed
    public ResponseEntity<Family> updateFamily(@RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to update Family : {}", family);
        if (family.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Family result = familyService.save(family);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, family.getId().toString()))
            .body(result);
    }

    /**
     * GET  /families : get all the families.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of families in body
     */
    @GetMapping("/families")
    @Timed
    public ResponseEntity<List<Family>> getAllFamilies(Pageable pageable) {
        log.debug("REST request to get a page of Families");
        Page<Family> page = familyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/families");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /families/:id : get the "id" family.
     *
     * @param id the id of the family to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the family, or with status 404 (Not Found)
     */
    @GetMapping("/families/{id}")
    @Timed
    public ResponseEntity<Family> getFamily(@PathVariable Long id) {
        log.debug("REST request to get Family : {}", id);
        Optional<Family> family = familyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(family);
    }

    /**
     * DELETE  /families/:id : delete the "id" family.
     *
     * @param id the id of the family to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/families/{id}")
    @Timed
    public ResponseEntity<Void> deleteFamily(@PathVariable Long id) {
        log.debug("REST request to delete Family : {}", id);
        familyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
