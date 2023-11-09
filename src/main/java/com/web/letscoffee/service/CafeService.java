package com.web.letscoffee.service;

import com.web.letscoffee.domain.Cafe;
import com.web.letscoffee.domain.CafeId;
import com.web.letscoffee.repository.CafeRepository;
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

