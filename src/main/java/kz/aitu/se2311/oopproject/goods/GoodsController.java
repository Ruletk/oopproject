package kz.aitu.se2311.oopproject.goods;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.aitu.se2311.oopproject.goods.dto.requests.GoodsCreationRequest;
import kz.aitu.se2311.oopproject.goods.dto.requests.GoodsEditionRequest;
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
@Tag(name = "Goods controller")
public class GoodsController {
    private final GoodsService goodsService;
    @Operation(
            description = "Get good by its name"
    )
    @PostMapping("/create")
    public GoodsResponse create(final @RequestBody @Valid GoodsCreationRequest request) {
        return new GoodsResponse(goodsService.createGood(request));
    }

    @PostMapping("/edit")
    public GoodsResponse change(final @RequestBody @Valid GoodsEditionRequest request) {
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
