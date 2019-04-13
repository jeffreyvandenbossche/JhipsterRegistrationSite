package be.jeffreyvdb.weddingsite.repository;

import be.jeffreyvdb.weddingsite.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Family entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Query("select family from Family family where accesscode.code =:accesscode")
    Optional<Family> findOneByAccesscode(@Param("accesscode") String accesscode);
}
