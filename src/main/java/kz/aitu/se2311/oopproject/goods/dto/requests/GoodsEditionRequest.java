package kz.aitu.se2311.oopproject.goods.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GoodsEditionRequest {
    @Schema(description = "Id good", example = "12")
    @NotNull
    private Long id;

    @Schema(description = "Good's name", example = "Intel Core i7 12700K")
    @Size(min = 10, max = 256, message = "Good's name must be between 10 and 256 characters long")
    private String name;

    @Schema(description = "Good's description", example = "The powerful processor for gaming, streaming and working")
    private String description;

    @Schema(description = "Cost of the good", example = "270000")
    private Long price;

    @Schema(description = "Id of the company where current user working", example = "120")
    @NotNull
    private Long companyId;
}
