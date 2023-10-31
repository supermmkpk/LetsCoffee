package com.graduate2.project.service;


/*
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
*/

import com.graduate2.project.domain.Favorite;
import com.graduate2.project.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService{

    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite findByTitle(String title){
        return favoriteRepository.findByTitle(title);
    }

    public List<Favorite> findAllFavorites(){
        return favoriteRepository.findAll();
    }

    public void saveFavorite(Favorite favorite){
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long id){
        favoriteRepository.deleteById(id);
    }
}