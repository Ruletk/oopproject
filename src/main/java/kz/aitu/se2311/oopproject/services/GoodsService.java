package kz.aitu.se2311.oopproject.services;

import kz.aitu.se2311.oopproject.entities.Good;
import kz.aitu.se2311.oopproject.repositories.GoodRepository;
import kz.aitu.se2311.oopproject.requests.GoodsChangeRequest;
import kz.aitu.se2311.oopproject.requests.GoodsCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodRepository goodRepository;

    public Good createGood(String name, String description, int price) {
        Good good = Good.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
        return createGood(good);
    }

    public Good createGood(Good good) {
        return save(good);
    }

    private Good save(Good good) {
        return goodRepository.save(good);
    }

    public Collection<Good> getGoodByName(String name) {
        return goodRepository.findByName(name);
    }

    public Good createGood(GoodsCreationRequest request) {

        return createGood(request.getName(), request.getDescription(), request.getPrice());
    }

    public Good changeGood(GoodsChangeRequest request) {
        Good good = (Good) goodRepository.findByName(request.getName());

        if (good != null) {
            good.setDescription(request.getDescription());
            good.setPrice(request.getPrice());

            return save(good);
        } else {
            return null;
        }
    }

    public void deleteGood(String name) {
        Good good = (Good) goodRepository.findByName(name);

        if (good != null) {
            goodRepository.delete(good);
        } else {
            System.out.println("The feature is not found");
        }

    }
}
