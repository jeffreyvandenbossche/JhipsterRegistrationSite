package be.jeffreyvdb.weddingsite.service.impl;

import be.jeffreyvdb.weddingsite.service.PartyPartService;
import be.jeffreyvdb.weddingsite.domain.PartyPart;
import be.jeffreyvdb.weddingsite.repository.PartyPartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PartyPart.
 */
@Service
@Transactional
public class PartyPartServiceImpl implements PartyPartService {

    private final Logger log = LoggerFactory.getLogger(PartyPartServiceImpl.class);

    private final PartyPartRepository partyPartRepository;

    public PartyPartServiceImpl(PartyPartRepository partyPartRepository) {
        this.partyPartRepository = partyPartRepository;
    }

    /**
     * Save a partyPart.
     *
     * @param partyPart the entity to save
     * @return the persisted entity
     */
    @Override
    public PartyPart save(PartyPart partyPart) {
        log.debug("Request to save PartyPart : {}", partyPart);
        return partyPartRepository.save(partyPart);
    }

    /**
     * Get all the partyParts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyPart> findAll() {
        log.debug("Request to get all PartyParts");
        return partyPartRepository.findAll();
    }


    /**
     * Get one partyPart by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyPart> findOne(Long id) {
        log.debug("Request to get PartyPart : {}", id);
        return partyPartRepository.findById(id);
    }

    /**
     * Delete the partyPart by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartyPart : {}", id);
        partyPartRepository.deleteById(id);
    }
}
