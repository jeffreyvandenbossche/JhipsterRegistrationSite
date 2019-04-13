package be.jeffreyvdb.weddingsite.service.impl;

import be.jeffreyvdb.weddingsite.domain.Accesscode;
import be.jeffreyvdb.weddingsite.repository.AccesscodeRepository;
import be.jeffreyvdb.weddingsite.service.AccesscodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Accesscode.
 */
@Service
@Transactional
public class AccesscodeServiceImpl implements AccesscodeService {

    private final Logger log = LoggerFactory.getLogger(AccesscodeServiceImpl.class);

    private final AccesscodeRepository accesscodeRepository;

    public AccesscodeServiceImpl(AccesscodeRepository accesscodeRepository) {
        this.accesscodeRepository = accesscodeRepository;
    }

    /**
     * Save a accesscode.
     *
     * @param accesscode the entity to save
     * @return the persisted entity
     */
    @Override
    public Accesscode save(Accesscode accesscode) {
        log.debug("Request to save Accesscode : {}", accesscode);
        return accesscodeRepository.save(accesscode);
    }

    /**
     * Get all the accesscodes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Accesscode> findAll() {
        log.debug("Request to get all Accesscodes");
        return accesscodeRepository.findAll();
    }

    /**
     * Get all the Accesscode with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public List<Accesscode> findAllWithEagerRelationships() {
        return accesscodeRepository.findAllWithEagerRelationships();
    }


    /**
     * Get one accesscode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Accesscode> findOne(Long id) {
        log.debug("Request to get Accesscode : {}", id);
        return accesscodeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public Optional<Accesscode> findOneByCode(String accessCode) {
        log.debug("Request to get Accesscode : {}", accessCode);
        return accesscodeRepository.findOneAccesscodeWithEagerRelationships(accessCode);
    }

    @Override
    public List<Accesscode> retrieveAllInfoforOneByCode(String accessCode) {
        log.debug("Request to get Accesscode : {}", accessCode);
        return accesscodeRepository.findOneAccesscodeWithEagerRelationshipsAndAllPersonsLinkedWithThisCode(accessCode);
    }

    /**
     * Delete the accesscode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accesscode : {}", id);
        accesscodeRepository.deleteById(id);
    }
}
