package kz.aitu.se2311.oopproject.companies.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Company creation request")
public class CompanyCreationRequest {
    @Schema(description = "Company name", example = "Apple")
    @Size(min = 1, max = 64, message = "Company name must be between 1 and 64 characters long")
    @NotBlank(message = "Company name cannot be blank")
    @NotNull
    @NotEmpty
    private String name;

    @Schema(description = "Company description", example = "Apple is a large international company")
    @NotBlank(message = "Company description cannot be blank")
    @NotEmpty
    @NotNull
    private String description;
}
