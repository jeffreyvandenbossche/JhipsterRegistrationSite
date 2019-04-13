package be.jeffreyvdb.weddingsite.repository;

import be.jeffreyvdb.weddingsite.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
