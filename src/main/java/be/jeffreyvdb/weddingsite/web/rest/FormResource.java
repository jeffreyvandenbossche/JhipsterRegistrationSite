package be.jeffreyvdb.weddingsite.web.rest;

import be.jeffreyvdb.weddingsite.domain.Accesscode;
import be.jeffreyvdb.weddingsite.domain.Family;
import be.jeffreyvdb.weddingsite.domain.PartyPart;
import be.jeffreyvdb.weddingsite.domain.Person;
import be.jeffreyvdb.weddingsite.service.AccesscodeService;
import be.jeffreyvdb.weddingsite.service.FamilyService;
import be.jeffreyvdb.weddingsite.service.PersonService;
import be.jeffreyvdb.weddingsite.web.rest.errors.BadRequestAlertException;
import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.*;


/**
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private static final String ENTITY_NAME = "person";
    private final Logger log = LoggerFactory.getLogger(FormResource.class);
    private final PersonService personService;
    private final AccesscodeService accesscodeService;
    private final FamilyService familyService;
    private static final Gson gson = new Gson();


    public FormResource(PersonService personService, AccesscodeService accesscodeService, FamilyService familyService) {
        this.personService = personService;
        this.accesscodeService = accesscodeService;
        this.familyService = familyService;
    }

    /**
     * POST  /people : Create a new person.
     *
     * @param form the person to create
     * @return the ResponseEntity with status 201 (Created) and with body the new person, or with status 400 (Bad Request) if the person has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form/{accesscode}")
    @Timed
    public ResponseEntity<String> createPerson(@PathVariable String accesscode, @RequestBody Object form) throws URISyntaxException {
        log.debug("REST request to save formdata in DB: {} for accesscode: {}", form, accesscode);
        if (form == null || accesscode.equalsIgnoreCase("")) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<Accesscode> accesscodeOptional = accesscodeService.findOneByCode(accesscode);
        if (accesscodeOptional.isPresent()) {
            Accesscode code = accesscodeOptional.get();
            String willAttend = (String) ((LinkedHashMap) form).get("willAttend");
            code.setWillAttend(Boolean.parseBoolean(willAttend));
            Accesscode result = accesscodeService.save(code);

            // if result error => trhrow statement if okay go further
            ArrayList<LinkedHashMap> personsList = (ArrayList<LinkedHashMap>) ((LinkedHashMap) form).get("persons");
            Optional<Family> familyOptional = familyService.findOneByAccessCode(accesscode);
            if (familyOptional.isPresent()) {
                Family family = familyOptional.get();
                List<Person> personsFromOneFamily = personService.findAllPersonsFromOneFamily(family.getId());
                for (Person personDB : personsFromOneFamily) {
                    Boolean toRemove = true;
                    for (LinkedHashMap person : personsList) {
                        if (!(person.get("id").equals(""))) {
                            if ((personDB.getId().equals(new Long((Integer) person.get("id"))))) {
                                toRemove = false;
//
                            }
                        }
                    }
                    if (toRemove) {
                        personService.delete(personDB.getId());
                    }
                }
                for (LinkedHashMap person : personsList) {
                    System.out.println(person);
                    if (person.get("id") == "") {

                        System.out.println("new Person");
                        Set<PartyPart> PartyPartSet = createPartyPartSet((ArrayList<LinkedHashMap>) person.get("partyParts"));
                        Person newPerson = new Person(person.get("firstName").toString(), (String) person.get("familyName"), person.get("foodRestrictions").toString(), family, PartyPartSet);
                        personService.save(newPerson);
                        System.out.println(newPerson);
                    } else {
                        Long id = new Long((Integer) person.get("id"));
                        Optional<Person> personOptional = personService.findOne(id);
                        if (personOptional.isPresent()) {
                            Person personDB = personOptional.get();
                            personDB.setFamilyName((String) person.get("familyName"));
                            personDB.setFirstName((String) person.get("firstName"));
                            personDB.setFoodRestriction((String) person.get("foodRestrictions"));
                            Set<PartyPart> partyPartSet = createPartyPartSet((ArrayList<LinkedHashMap>) person.get("partyParts"));
                            personDB.setPartyParts(partyPartSet);
                            personService.save(personDB);
                        }
                    }
                }
            }
        } else {
            throw new BadRequestAlertException("Invalid accessCode", "Accesscode", "idNotExist");
        }
        return ResponseEntity.ok()
            .body(gson.toJson("This is a String"));
    }

    private Set<PartyPart> createPartyPartSet(ArrayList<LinkedHashMap> partyParts) {
        Set<PartyPart> partyPartSet = new HashSet<>();
        for (LinkedHashMap partyPart : partyParts) {
            System.out.println(partyPart);
            if (partyPart.get("checked").equals(true)) {
                PartyPart part = new PartyPart();
                part.setId(new Long((Integer) partyPart.get("id")));
                part.setPartName((String) partyPart.get("name"));
                partyPartSet.add(part);
            }
        }
        return partyPartSet;
    }
}
