package be.jeffreyvdb.weddingsite.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.jeffreyvdb.weddingsite.domain.PartyPart;
import be.jeffreyvdb.weddingsite.service.PartyPartService;
import be.jeffreyvdb.weddingsite.web.rest.errors.BadRequestAlertException;
import be.jeffreyvdb.weddingsite.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PartyPart.
 */
@RestController
@RequestMapping("/api")
public class PartyPartResource {

    private final Logger log = LoggerFactory.getLogger(PartyPartResource.class);

    private static final String ENTITY_NAME = "partyPart";

    private final PartyPartService partyPartService;

    public PartyPartResource(PartyPartService partyPartService) {
        this.partyPartService = partyPartService;
    }

    /**
     * POST  /party-parts : Create a new partyPart.
     *
     * @param partyPart the partyPart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partyPart, or with status 400 (Bad Request) if the partyPart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/party-parts")
    @Timed
    public ResponseEntity<PartyPart> createPartyPart(@RequestBody PartyPart partyPart) throws URISyntaxException {
        log.debug("REST request to save PartyPart : {}", partyPart);
        if (partyPart.getId() != null) {
            throw new BadRequestAlertException("A new partyPart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartyPart result = partyPartService.save(partyPart);
        return ResponseEntity.created(new URI("/api/party-parts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /party-parts : Updates an existing partyPart.
     *
     * @param partyPart the partyPart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partyPart,
     * or with status 400 (Bad Request) if the partyPart is not valid,
     * or with status 500 (Internal Server Error) if the partyPart couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/party-parts")
    @Timed
    public ResponseEntity<PartyPart> updatePartyPart(@RequestBody PartyPart partyPart) throws URISyntaxException {
        log.debug("REST request to update PartyPart : {}", partyPart);
        if (partyPart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartyPart result = partyPartService.save(partyPart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partyPart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /party-parts : get all the partyParts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partyParts in body
     */
    @GetMapping("/party-parts")
    @Timed
    public List<PartyPart> getAllPartyParts() {
        log.debug("REST request to get all PartyParts");
        return partyPartService.findAll();
    }

    /**
     * GET  /party-parts/:id : get the "id" partyPart.
     *
     * @param id the id of the partyPart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partyPart, or with status 404 (Not Found)
     */
    @GetMapping("/party-parts/{id}")
    @Timed
    public ResponseEntity<PartyPart> getPartyPart(@PathVariable Long id) {
        log.debug("REST request to get PartyPart : {}", id);
        Optional<PartyPart> partyPart = partyPartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partyPart);
    }

    /**
     * DELETE  /party-parts/:id : delete the "id" partyPart.
     *
     * @param id the id of the partyPart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/party-parts/{id}")
    @Timed
    public ResponseEntity<Void> deletePartyPart(@PathVariable Long id) {
        log.debug("REST request to delete PartyPart : {}", id);
        partyPartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
