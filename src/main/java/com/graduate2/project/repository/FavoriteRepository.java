package com.graduate2.project.repository;

import com.graduate2.project.domain.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoriteRepository {
    private final EntityManager em;

    public void save(Favorite favorite) {
        if(favorite.getId() == null) {
            em.persist(favorite);
        }
        else {
            em.merge(favorite);
        }
    }

    public Favorite findOne(Long id) {
       return em.find(Favorite.class, id);
    }

    public List<Favorite> findAll() {
        return em.createQuery("select f from Favorite f", Favorite.class)
                .getResultList();
    }
}
