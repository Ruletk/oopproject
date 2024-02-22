package kz.aitu.se2311.oopproject.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsRequest {
    private Long id;
    private String name;
    private String description;
    private Integer price;
}
