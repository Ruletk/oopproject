package kz.aitu.se2311.oopproject.repositories;

import kz.aitu.se2311.oopproject.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
