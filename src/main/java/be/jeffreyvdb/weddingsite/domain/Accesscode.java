package be.jeffreyvdb.weddingsite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Accesscode.
 */
@Entity
@Table(name = "accesscode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accesscode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "will_attend")
    private Boolean willAttend;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "accesscode_party_part",
        joinColumns = @JoinColumn(name = "accesscodes_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "party_parts_id", referencedColumnName = "id"))
    private Set<PartyPart> partyParts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Accesscode code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isWillAttend() {
        return willAttend;
    }

    public Boolean getWillAttend() {
        return willAttend;
    }

    public Accesscode willAttend(Boolean willAttend) {
        this.willAttend = willAttend;
        return this;
    }

    public void setWillAttend(Boolean willAttend) {
        this.willAttend = willAttend;
    }

    public Set<PartyPart> getPartyParts() {
        return partyParts;
    }

    public Accesscode partyParts(Set<PartyPart> partyParts) {
        this.partyParts = partyParts;
        return this;
    }

    public Accesscode addPartyPart(PartyPart partyPart) {
        this.partyParts.add(partyPart);
        partyPart.getAccesscodes().add(this);
        return this;
    }

    public Accesscode removePartyPart(PartyPart partyPart) {
        this.partyParts.remove(partyPart);
        partyPart.getAccesscodes().remove(this);
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
        Accesscode accesscode = (Accesscode) o;
        if (accesscode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accesscode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Accesscode{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", willAttend='" + isWillAttend() + "'" +
            "}";
    }
}
