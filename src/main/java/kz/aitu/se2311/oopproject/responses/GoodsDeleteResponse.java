package kz.aitu.se2311.oopproject.responses;

import kz.aitu.se2311.oopproject.requests.GoodsDeleteRequest;
import lombok.Data;

@Data
public class GoodsDeleteResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;


    public GoodsDeleteResponse(GoodsDeleteRequest good) {
        this.id = good.getId();
        this.name = good.getName();
        this.price = good.getPrice();
        this.description = good.getDescription();
    }
}

