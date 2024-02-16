package kz.aitu.se2311.oopproject.responses;

import kz.aitu.se2311.oopproject.entities.Good;
import lombok.Data;

@Data
public class GoodsCreateResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;

    public GoodsCreateResponse(Good good) {
        setId(good.getId());
        setName(good.getName());
        setPrice(good.getPrice());
        setDescription(good.getDescription());
    }
}