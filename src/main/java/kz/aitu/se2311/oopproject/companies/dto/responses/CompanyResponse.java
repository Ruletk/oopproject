package kz.aitu.se2311.oopproject.companies.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.aitu.se2311.oopproject.entities.Company;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Response for company. Providing schema for creation, editing and getting responses.")
public class CompanyResponse {
    @Schema(description = "Company id from database", example = "120")
    private Long id;

    @Schema(description = "Name of the company", example = "Apple inc.")
    private String name;

    @Schema(description = "URL slug of company", example = "apple-inc")
    private String slug;

    @Schema(
            description = "Large text for company description",
            example = "Apple is a large international company that creating many types of tech"
    )
    private String description;

    @Schema(description = "Date of creating company in database", example = "2024-02-26T16:53:36.725+00:00")
    private Date createdAt;

    @Schema(description = "Date of updating any field in company", example = "2024-02-26T18:53:36.725+00:00")
    private Date updatedAt;

    @Schema(description = "Username of company's owner", example = "test_username")
    private String owner;

    public CompanyResponse(Company company) {
        setId(company.getId());
        setName(company.getName());
        setSlug(company.getSlug());
        setDescription(company.getDescription());
        setCreatedAt(company.getCreatedAt());
        setUpdatedAt(company.getUpdatedAt());
        setOwner(company.getOwner().getUsername());
    }
}
