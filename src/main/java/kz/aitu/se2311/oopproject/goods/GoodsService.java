package kz.aitu.se2311.oopproject.goods;

import com.github.slugify.Slugify;
import jakarta.persistence.EntityNotFoundException;
import kz.aitu.se2311.oopproject.entities.Company;
import kz.aitu.se2311.oopproject.entities.Good;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.goods.dto.requests.GoodsCreationRequest;
import kz.aitu.se2311.oopproject.goods.dto.requests.GoodsEditionRequest;
import kz.aitu.se2311.oopproject.goods.dto.requests.GoodsRequest;
import kz.aitu.se2311.oopproject.repositories.CompanyRepository;
import kz.aitu.se2311.oopproject.repositories.GoodRepository;
import kz.aitu.se2311.oopproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    private final GoodRepository goodRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final Slugify slugify;

    public Good createGood(GoodsCreationRequest request) {
        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(EntityNotFoundException::new);
        if (!company.getOwner().getId().equals(getCurrentUser().getId()))
            throw new AccessDeniedException("You don't have access to this page");

        Good good = Good.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName() + "-" +(new Date()).hashCode()))
                .description(request.getDescription())
                .price(request.getPrice())
                .created_at(new Date())
                .updated_at(new Date())
                .company(company)
                .build();

        return createGood(good);
    }

    public Good changeGood(GoodsEditionRequest request) {
        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(EntityNotFoundException::new);
        if (!company.getOwner().getId().equals(getCurrentUser().getId()))
            throw new AccessDeniedException("You don't have access to this page");

        Good good = goodRepository.findById(request.getId()).orElseThrow(EntityNotFoundException::new);

        BeanUtils.copyProperties(request, good);
        good.setUpdated_at(new Date());

        return save(good);
    }

    public Good searchGoodStartsWith(String s) {
        return goodRepository.findByNameStartsWith(s).orElseThrow(EntityNotFoundException::new);
    }

    public Optional<Good> getGoodByName(String name) {
        return goodRepository.findByName(name);
    }

    public Optional<Good> getGoodBySlug(String slug) {
        return goodRepository.findBySlug(slug);
    }

    public void deleteGood(String name) {
        Good good = goodRepository.findByName(name).orElseThrow(EntityNotFoundException::new);

        goodRepository.delete(good);

    }

    private Good createGood(Good good) {
        return save(good);
    }

    private Good save(Good good) {
        return goodRepository.save(good);
    }

    private User getCurrentUser() {
        return userRepository.getUserByUsername(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName()
                )
                .orElseThrow(EntityNotFoundException::new);
    }
}
