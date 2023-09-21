package com.graduate2.project.service;

import com.graduate2.project.domain.Promotion;
import com.graduate2.project.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public Long save(Promotion promotion) {
        promotionRepository.save(promotion);
        return promotion.getId();
    }

    public Promotion findOne(Long id) {
        return promotionRepository.findOne(id);
    }

    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

}
