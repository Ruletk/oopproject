package kz.aitu.se2311.oopproject.goods;

import kz.aitu.se2311.oopproject.entities.Good;
import kz.aitu.se2311.oopproject.repositories.GoodRepository;
import kz.aitu.se2311.oopproject.goods.dto.requests.GoodsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    private final GoodRepository goodRepository;

    public Good createGood(String name, String description, int price) {
        GoodsRequest good = GoodsRequest.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
        return createGood(good);
    }

    public Good createGood(GoodsRequest request) {

        return createGood(request.getName(), request.getDescription(), request.getPrice());
    }

    public Optional<Good> getGoodByName(String name) {
        return goodRepository.findByName(name);
    }

    public Optional<Good> getGoodBySlug(String slug) {
        return goodRepository.findBySlug(slug);
    }

    public Good changeGood(GoodsRequest request) {
        Optional<Good> optionalGood = goodRepository.findByName(request.getName());

        if (optionalGood.isEmpty()) {
            return null;
        }
        Good good = optionalGood.get();
        good.setDescription(request.getDescription());
        good.setPrice(request.getPrice());

        return save(good);
    }

    public void deleteGood(String name) {
        Optional<Good> good = goodRepository.findByName(name);

        good.ifPresent(goodRepository::delete);

    }

    public String createSlug(String input) {
        String toSlug = input.replaceAll("[^a-zA-Z0-9\\s-]", "")
                .replaceAll("\\s+", " ")
                .toLowerCase()
                .trim()
                .replaceAll("\\s", "-");


        return toSlug;
    }
    private Good save(Good good) {
        return goodRepository.save(good);
    }


}
