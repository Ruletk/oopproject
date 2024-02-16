package kz.aitu.se2311.oopproject.responses;

import kz.aitu.se2311.oopproject.entities.Good;
import lombok.Data;

@Data
public class GoodsChangeResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;

    public GoodsChangeResponse(Good good) {
        setId(id);
        setName(name);
        setPrice(price);
        setDescription(description);
    }
}
