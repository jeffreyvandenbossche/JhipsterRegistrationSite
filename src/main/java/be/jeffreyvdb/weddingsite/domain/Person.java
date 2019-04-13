package be.jeffreyvdb.weddingsite.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "food_restriction")
    private String foodRestriction;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Family family;

    public Person() {
    }

    public Person(String firstName, String familyName, String foodRestriction, Family family, Set<PartyPart> partyParts) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.foodRestriction = foodRestriction;
        this.family = family;
        this.partyParts = partyParts;
    }

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_party_part",
               joinColumns = @JoinColumn(name = "people_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "party_parts_id", referencedColumnName = "id"))
    private Set<PartyPart> partyParts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Person familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFoodRestriction() {
        return foodRestriction;
    }

    public void setFoodRestriction(String foodRestriction) {
        this.foodRestriction = foodRestriction;
    }

    public Person foodRestriction(String foodRestriction) {
        this.foodRestriction = foodRestriction;
        return this;
    }

    public Family getFamily() {
        return family;
    }

    public Person family(Family family) {
        this.family = family;
        return this;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Set<PartyPart> getPartyParts() {
        return partyParts;
    }

    public Person partyParts(Set<PartyPart> partyParts) {
        this.partyParts = partyParts;
        return this;
    }

    public Person addPartyPart(PartyPart partyPart) {
        this.partyParts.add(partyPart);
        partyPart.getPeople().add(this);
        return this;
    }

    public Person removePartyPart(PartyPart partyPart) {
        this.partyParts.remove(partyPart);
        partyPart.getPeople().remove(this);
        return this;
    }

    public void setPartyParts(Set<PartyPart> partyParts) {
        this.partyParts = partyParts;
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
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", foodRestriction='" + getFoodRestriction() + "'" +
            "}";
    }
}
