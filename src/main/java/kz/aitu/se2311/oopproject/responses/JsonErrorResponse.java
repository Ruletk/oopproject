package kz.aitu.se2311.oopproject.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response with json errors")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonErrorResponse {
    @Schema(description = "Any errors or notifications")
    private String message;
}
