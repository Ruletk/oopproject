package kz.aitu.se2311.oopproject.goods;

import io.swagger.v3.oas.annotations.Operation;
import kz.aitu.se2311.oopproject.goods.dto.requests.GoodsRequest;
import kz.aitu.se2311.oopproject.goods.dto.responses.GoodsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;
    @Operation(
            description = "Get good by its name"
    )

    @PostMapping("/create")
    public GoodsResponse create(final @RequestBody GoodsRequest request) {
        return new GoodsResponse(goodsService.createGood(request));
    }

    @PostMapping("/edit")
    public GoodsResponse change(final @RequestBody GoodsRequest request) {
        return new GoodsResponse(goodsService.changeGood(request));
    }

    @PostMapping("/delete")
    public GoodsResponse delete(@RequestBody GoodsRequest request) {
        goodsService.deleteGood(request.getName());
        GoodsResponse goodsDeleteResponse;
        goodsDeleteResponse = GoodsResponse.builder().message("Good was deleted").build();
        return goodsDeleteResponse;
    }
}
