package be.jeffreyvdb.weddingsite.service;

import be.jeffreyvdb.weddingsite.domain.PartyPart;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PartyPart.
 */
public interface PartyPartService {

    /**
     * Save a partyPart.
     *
     * @param partyPart the entity to save
     * @return the persisted entity
     */
    PartyPart save(PartyPart partyPart);

    /**
     * Get all the partyParts.
     *
     * @return the list of entities
     */
    List<PartyPart> findAll();


    /**
     * Get the "id" partyPart.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PartyPart> findOne(Long id);

    /**
     * Delete the "id" partyPart.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
