package be.jeffreyvdb.weddingsite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PartyPart.
 */
@Entity
@Table(name = "party_part")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartyPart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_name")
    private String partName;

    @ManyToMany(mappedBy = "partyParts")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Person> people = new HashSet<>();

    @ManyToMany(mappedBy = "partyParts")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Accesscode> accesscodes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public PartyPart partName(String partName) {
        this.partName = partName;
        return this;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public PartyPart people(Set<Person> people) {
        this.people = people;
        return this;
    }

    public PartyPart addPerson(Person person) {
        this.people.add(person);
        person.getPartyParts().add(this);
        return this;
    }

    public PartyPart removePerson(Person person) {
        this.people.remove(person);
        person.getPartyParts().remove(this);
        return this;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public Set<Accesscode> getAccesscodes() {
        return accesscodes;
    }

    public void setAccesscodes(Set<Accesscode> accesscodes) {
        this.accesscodes = accesscodes;
    }

    public PartyPart accesscodes(Set<Accesscode> accesscodes) {
        this.accesscodes = accesscodes;
        return this;
    }

    public PartyPart addAccesscode(Accesscode accesscode) {
        this.accesscodes.add(accesscode);
        accesscode.getPartyParts().add(this);
        return this;
    }

    public PartyPart removeAccesscode(Accesscode accesscode) {
        this.accesscodes.remove(accesscode);
        accesscode.getPartyParts().remove(this);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PartyPart partyPart = (PartyPart) o;
        if (partyPart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partyPart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartyPart{" +
            "id=" + getId() +
            ", partName='" + getPartName() + "'" +
            "}";
    }
}
