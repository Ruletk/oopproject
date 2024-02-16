package kz.aitu.se2311.oopproject.requests;

import lombok.Data;

@Data
public class GoodsChangeRequest {
    private Long id;
    private String name;
    private String description;
    private Integer price;
}
