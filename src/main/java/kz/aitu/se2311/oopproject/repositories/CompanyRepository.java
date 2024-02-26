package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> getByName(String name);

    @Query("SELECT c from Company AS c where c.slug = :slug and c.deleted_at is null")
    Optional<Company> getBySlug(String slug);

    @Query("SELECT c from Company AS c where c.id = :id and c.deleted_at is null")
    Optional<Company> findByIdSafe(Long id);
}
