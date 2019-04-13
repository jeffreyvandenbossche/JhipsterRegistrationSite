package be.jeffreyvdb.weddingsite.repository;

import be.jeffreyvdb.weddingsite.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "select distinct person from Person person left join fetch person.partyParts",
        countQuery = "select count(distinct person) from Person person")
    Page<Person> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct person from Person person left join fetch person.partyParts")
    List<Person> findAllWithEagerRelationships();

    @Query("select person from Person person left join fetch person.partyParts where person.id =:id")
    Optional<Person> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select person from Person person where family.id =:id")
    List<Person> findAllWithSameFamily(@Param("id") Long id);

    /*@Query("select person from Person person where accesscode.code =:accesscode")
    List<Person> findAllWithSameAccessCode(@Param("accesscode") String accesscode);*/

}
