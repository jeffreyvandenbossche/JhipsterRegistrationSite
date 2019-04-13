package be.jeffreyvdb.weddingsite.repository;

import be.jeffreyvdb.weddingsite.domain.PartyPart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyPart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyPartRepository extends JpaRepository<PartyPart, Long> {

}
