package com.graduate2.project.service;

import com.graduate2.project.domain.Favorite;
import com.graduate2.project.domain.User;
import com.graduate2.project.exception.NoSuchUserException;
import com.graduate2.project.repository.FavoriteRepository;
import com.graduate2.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addFavorite(Long userId, String storeName) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new NoSuchUserException("No Such User");
        }
        Favorite favorite = Favorite.createUserFavoriteStore(user, storeName);

        favoriteRepository.save(favorite);
        return favorite.getId();
    }

    @Transactional
    public void cancelFavorite(Long id) {
        favoriteRepository.cancel(id);
    }

    public Favorite findOne(Long id) {
        return favoriteRepository.findOne(id);
    }
    public List<Favorite> findAllById(Long id) {
        return favoriteRepository.findAllById(id);
    }

    public List<Favorite> findByStoreName(String storeName) {
        return favoriteRepository.findByStoreName(storeName);
    }

    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }

}
