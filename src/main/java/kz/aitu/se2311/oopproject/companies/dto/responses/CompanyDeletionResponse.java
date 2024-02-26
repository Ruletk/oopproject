package kz.aitu.se2311.oopproject.companies.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response that contains message of successful company deletion")
public class CompanyDeletionResponse {
    @Schema(description = "Message of deletion", example = "company was deleted")
    private String message;
}
