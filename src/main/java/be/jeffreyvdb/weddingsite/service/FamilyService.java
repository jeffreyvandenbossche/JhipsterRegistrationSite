package be.jeffreyvdb.weddingsite.service;

import be.jeffreyvdb.weddingsite.domain.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Family.
 */
public interface FamilyService {

    /**
     * Save a family.
     *
     * @param family the entity to save
     * @return the persisted entity
     */
    Family save(Family family);

    /**
     * Get all the families.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Family> findAll(Pageable pageable);


    /**
     * Get the "id" family.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Family> findOne(Long id);

    /**
     * Get the "id" family.
     *
     * @param accesscode the id of the entity
     * @return the entity
     */
    Optional<Family> findOneByAccessCode(String accesscode);

    /**
     * Delete the "id" family.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
