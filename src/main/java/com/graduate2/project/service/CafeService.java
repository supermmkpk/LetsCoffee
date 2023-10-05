package com.graduate2.project.service;

import com.graduate2.project.domain.Cafe;
import com.graduate2.project.domain.CafeId;
import com.graduate2.project.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;

    @Transactional
    public void save(Cafe cafe) {
        cafeRepository.save(cafe);
    }

    public Cafe findByOne(CafeId id) {
        return cafeRepository.findOne(id);
    }
}

