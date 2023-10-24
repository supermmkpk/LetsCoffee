package com.graduate2.project.service;

import com.graduate2.project.domain.Favorite;
import com.graduate2.project.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public Long save(Favorite favorite) {
        favoriteRepository.save(favorite);
        return favorite.getId();
    }

    public Favorite findOne(Long id) {
        return favoriteRepository.findOne(id);
    }

    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }

}
