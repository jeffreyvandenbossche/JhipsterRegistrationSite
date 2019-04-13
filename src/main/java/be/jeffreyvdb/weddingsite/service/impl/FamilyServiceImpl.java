package be.jeffreyvdb.weddingsite.service.impl;

import be.jeffreyvdb.weddingsite.domain.Family;
import be.jeffreyvdb.weddingsite.repository.FamilyRepository;
import be.jeffreyvdb.weddingsite.service.FamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Family.
 */
@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

    private final Logger log = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyRepository familyRepository;

    public FamilyServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    /**
     * Save a family.
     *
     * @param family the entity to save
     * @return the persisted entity
     */
    @Override
    public Family save(Family family) {
        log.debug("Request to save Family : {}", family);
        return familyRepository.save(family);
    }

    /**
     * Get all the families.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Family> findAll(Pageable pageable) {
        log.debug("Request to get all Families");
        return familyRepository.findAll(pageable);
    }


    /**
     * Get one family by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Family> findOne(Long id) {
        log.debug("Request to get Family : {}", id);
        return familyRepository.findById(id);
    }

    @Override
    public Optional<Family> findOneByAccessCode(String accesscode) {
        log.debug("Request to get Family : {}", accesscode);
        return familyRepository.findOneByAccesscode(accesscode);
    }

    /**
     * Delete the family by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Family : {}", id);
        familyRepository.deleteById(id);
    }
}
