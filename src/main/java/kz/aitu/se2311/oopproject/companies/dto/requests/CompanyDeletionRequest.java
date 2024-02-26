package kz.aitu.se2311.oopproject.companies.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for delete company by id")
public class CompanyDeletionRequest {
    @Schema(description = "Company id", example = "0")
    @NotNull
    private Long id;
}
