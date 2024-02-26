package kz.aitu.se2311.oopproject.companies.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyEditionRequest {
    @Schema(description = "Id of the company", example = "120")
    @NotNull
    private Long id;

    @Schema(description = "Company name", example = "Apple")
    @Size(min = 1, max = 64, message = "Company name must be between 1 and 64 characters long")
    private String name;

    @Schema(description = "Company description", example = "Apple is a large international company")
    private String description;
}
