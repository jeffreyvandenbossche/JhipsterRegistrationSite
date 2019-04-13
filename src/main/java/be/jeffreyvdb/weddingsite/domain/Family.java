package be.jeffreyvdb.weddingsite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Family.
 */
@Entity
@Table(name = "family")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Family implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "city")
    private String city;

    @OneToOne    @JoinColumn(unique = true)
    private Accesscode accesscode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Family familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getStreetName() {
        return streetName;
    }

    public Family streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public Family houseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public Family zipCode(Integer zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public Family city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Accesscode getAccesscode() {
        return accesscode;
    }

    public Family accesscode(Accesscode accesscode) {
        this.accesscode = accesscode;
        return this;
    }

    public void setAccesscode(Accesscode accesscode) {
        this.accesscode = accesscode;
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
        Family family = (Family) o;
        if (family.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), family.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Family{" +
            "id=" + getId() +
            ", familyName='" + getFamilyName() + "'" +
            ", streetName='" + getStreetName() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", zipCode=" + getZipCode() +
            ", city='" + getCity() + "'" +
            "}";
    }
}
