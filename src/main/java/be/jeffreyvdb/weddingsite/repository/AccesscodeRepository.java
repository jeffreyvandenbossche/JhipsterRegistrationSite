package be.jeffreyvdb.weddingsite.repository;

import be.jeffreyvdb.weddingsite.domain.Accesscode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Accesscode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccesscodeRepository extends JpaRepository<Accesscode, Long> {

    @Query(value = "select distinct accesscode from Accesscode accesscode left join fetch accesscode.partyParts",
        countQuery = "select count(distinct accesscode) from Accesscode accesscode")
    Page<Accesscode> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct accesscode from Accesscode accesscode left join fetch accesscode.partyParts")
    List<Accesscode> findAllWithEagerRelationships();

    @Query("select accesscode from Accesscode accesscode left join fetch accesscode.partyParts where accesscode.id =:id")
    Optional<Accesscode> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select accesscode from Accesscode accesscode left join fetch accesscode.partyParts where accesscode.code =:accesscode")
    Optional<Accesscode> findOneAccesscodeWithEagerRelationships(@Param("accesscode") String accesscode);


    @Query("select person.id " +
        "from Accesscode as accesscode, Person as person where accesscode.code =:accesscode")
    List<Accesscode> findOneAccesscodeWithEagerRelationshipsAndAllPersonsLinkedWithThisCode(@Param("accesscode") String accesscode);

}
