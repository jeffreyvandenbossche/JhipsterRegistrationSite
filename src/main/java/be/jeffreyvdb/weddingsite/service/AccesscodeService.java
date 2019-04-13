package be.jeffreyvdb.weddingsite.service;

import be.jeffreyvdb.weddingsite.domain.Accesscode;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Accesscode.
 */
public interface AccesscodeService {

    /**
     * Save a accesscode.
     *
     * @param accesscode the entity to save
     * @return the persisted entity
     */
    Accesscode save(Accesscode accesscode);

    /**
     * Get all the accesscodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    List<Accesscode> findAll();

    /**
     * Get all the Accesscode with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    List<Accesscode> findAllWithEagerRelationships();

    /**
     * Get the "id" accesscode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Accesscode> findOne(Long id);

    /**
     * Get the "id" accesscode.
     *
     * @param accessCode the accesscode of the entity
     * @return the entity
     */
    Optional<Accesscode> findOneByCode(String accessCode);

    /**
     * Get the "id" accesscode.
     *
     * @param accessCode the accesscode of the entity
     * @return the entity
     */
    List<Accesscode> retrieveAllInfoforOneByCode(String accessCode);

    /**
     * Delete the "id" accesscode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
