package kz.aitu.se2311.oopproject.companies;

import com.github.slugify.Slugify;
import jakarta.persistence.EntityNotFoundException;
import kz.aitu.se2311.oopproject.companies.dto.requests.CompanyCreationRequest;
import kz.aitu.se2311.oopproject.companies.dto.requests.CompanyDeletionRequest;
import kz.aitu.se2311.oopproject.companies.dto.requests.CompanyEditionRequest;
import kz.aitu.se2311.oopproject.companies.dto.responses.CompanyDeletionResponse;
import kz.aitu.se2311.oopproject.companies.dto.responses.CompanyResponse;
import kz.aitu.se2311.oopproject.entities.Company;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.repositories.CompanyRepository;
import kz.aitu.se2311.oopproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final Slugify slugify;

    public List<CompanyResponse> getAllCompanies() {
        return companyRepository
                .findAll(Sort.by(Sort.Direction.DESC, "name"))
                .stream()
                .map(CompanyResponse::new)
                .toList();
    }

    /**
     * @param name Name of the company.
     * @return Returns CompanyResponse objects with all data of the company.
     * If company does not exist, throws handled 404 error
     */
    public CompanyResponse getCompanyByName(String name) {
        Company company = companyRepository.getByName(name).orElseThrow(EntityNotFoundException::new);
        return new CompanyResponse(company);
    }

    public CompanyResponse getCompanyBySlug(String companySlug) {
        Company company = companyRepository.getBySlug(companySlug).orElseThrow(EntityNotFoundException::new);
        return new CompanyResponse(company);
    }

    public CompanyResponse createCompany(CompanyCreationRequest request) {
        try {
            getCompanyByName(request.getName());
            throw new DataIntegrityViolationException("Company with this name already exists");
        } catch (EntityNotFoundException ignore) {
        }

        Company company = Company.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .description(request.getDescription())
                .createdAt(new Date())
                .updatedAt(new Date())
                .owner(getCurrentUser())
                .build();
        company = save(company);
        return new CompanyResponse(company);
    }

    public CompanyResponse editCompany(CompanyEditionRequest request) {
        Company company = companyRepository.findByIdSafe(request.getId()).orElseThrow(EntityNotFoundException::new);
        if (!company.getOwner().getId().equals(getCurrentUser().getId()))
            throw new AccessDeniedException("You don't have access to this page");

        BeanUtils.copyProperties(request, company);
        if (request.getName() != null)
            company.setSlug(slugify.slugify(request.getName()));

        company.setUpdatedAt(new Date());

        company = save(company);
        return new CompanyResponse(company);
    }

    public CompanyDeletionResponse deleteCompany(CompanyDeletionRequest request) {
        Company company = companyRepository.findByIdSafe(request.getId()).orElseThrow(EntityNotFoundException::new);
        if (!company.getOwner().getId().equals(getCurrentUser().getId()))
            throw new AccessDeniedException("You don't have access to this page");
        delete(company);
        return new CompanyDeletionResponse("company was deleted");
    }

    private Company save(Company company) {
        return companyRepository.save(company);
    }

    private Company delete(Company company) {
        company.setDeleted_at(new Date());
        return save(company);
    }

    private User getCurrentUser() {
        return userRepository.getUserByUsername(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        ).orElseThrow(EntityNotFoundException::new);
    }
}
