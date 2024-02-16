package kz.aitu.se2311.oopproject.controllers;

import kz.aitu.se2311.oopproject.requests.GoodsChangeRequest;
import kz.aitu.se2311.oopproject.requests.GoodsCreationRequest;
import kz.aitu.se2311.oopproject.requests.GoodsDeleteRequest;
import kz.aitu.se2311.oopproject.responses.GoodsChangeResponse;
import kz.aitu.se2311.oopproject.responses.GoodsCreateResponse;
import kz.aitu.se2311.oopproject.responses.GoodsDeleteResponse;
import kz.aitu.se2311.oopproject.services.GoodsService;
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

    @PostMapping("/create")
    public GoodsCreateResponse create(final @RequestBody GoodsCreationRequest request) {
        return new GoodsCreateResponse(goodsService.createGood(request));
    }

    @PostMapping("/edit")
    public GoodsChangeResponse change(final @RequestBody GoodsChangeRequest request) {
        return new GoodsChangeResponse(goodsService.changeGood(request));
    }

    @PostMapping("/delete")
    public GoodsDeleteResponse delete(@RequestBody GoodsDeleteRequest request) {
        goodsService.deleteGood(request.getName());
        GoodsDeleteResponse goodsDeleteResponse = new GoodsDeleteResponse(request);
        return goodsDeleteResponse;
    }
}