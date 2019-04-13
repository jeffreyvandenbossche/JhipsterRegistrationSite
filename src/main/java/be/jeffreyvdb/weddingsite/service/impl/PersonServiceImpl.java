package be.jeffreyvdb.weddingsite.service.impl;

import be.jeffreyvdb.weddingsite.domain.Person;
import be.jeffreyvdb.weddingsite.repository.PersonRepository;
import be.jeffreyvdb.weddingsite.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Person.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Save a person.
     *
     * @param person the entity to save
     * @return the persisted entity
     */
    @Override
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        return personRepository.save(person);
    }

    /**
     * Get all the people.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Person> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        return personRepository.findAll(pageable);
    }

    /**
     * Get all the Person with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Person> findAllWithEagerRelationships(Pageable pageable) {
        return personRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one person by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> findAllPersonsFromOneFamily(Long id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findAllWithSameFamily(id);
    }

    @Override
    public List<Person> findAllPersonsFromOneAccesscode(String accesscode) {
        log.debug("Request to get Persons : {}", accesscode);
//         personRepository.findAllWithSameAccessCode(accesscode);
    return null;
    }
}
