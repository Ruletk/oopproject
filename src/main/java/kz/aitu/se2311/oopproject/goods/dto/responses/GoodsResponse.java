package kz.aitu.se2311.oopproject.goods.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import kz.aitu.se2311.oopproject.entities.Good;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;

    private String message;

    public GoodsResponse(Good good) {
        setId(good.getId());
        setName(good.getName());
        setPrice(good.getPrice());
        setDescription(good.getDescription());
    }
}