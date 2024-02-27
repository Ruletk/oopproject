package kz.aitu.se2311.oopproject.goods.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Good creation request")
public class GoodsCreationRequest {
    @Schema(description = "Good's name", example = "Intel Core i7 12700K")
    @Size(min = 10, max = 256, message = "Good's name must be between 10 and 256 characters long")
    @NotBlank(message = "Good's name cannot be blank")
    @NotNull
    @NotEmpty
    private String name;

    @Schema(description = "Good's description", example = "The powerful processor for gaming, streaming and working")
    @NotBlank(message = "Good's description cannot be blank")
    @NotNull
    @NotEmpty
    private String description;

    @Schema(description = "Cost of the good", example = "270000")
    @NotNull
    private Integer price;

    @Schema(description = "Id of the company where current user working", example = "120")
    @NotNull
    private Long companyId;
}
