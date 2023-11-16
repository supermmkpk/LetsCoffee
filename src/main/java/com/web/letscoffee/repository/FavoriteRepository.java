package com.web.letscoffee.repository;

import com.web.letscoffee.domain.Favorite;
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

    public void cancel(Long id) {
        Favorite findFavorite = em.find(Favorite.class, id);
        findFavorite.cancel(); //user의 favoriteCount를 감소시킵니다.
        em.remove(findFavorite); //해당 행을 삭제합니다.
    }

    public Favorite findOne(Long id) {
       return em.find(Favorite.class, id);
    }

    public List<Favorite> findAllById(Long id) {
        return em.createQuery("select f from Favorite f where f.user.id = :id", Favorite.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Favorite findById(Long id){
        return em.find(Favorite.class, id);
    }

    public List<Favorite> findByStoreNameAndUserId(String storeName, Long userId) {
        String sql = "SELECT * FROM favorite WHERE store_name = \'" + storeName + "\' AND user_id = " + userId;
        return em.createNativeQuery(sql)
                .getResultList();
    }

    public List<Favorite> findAll() {
        return em.createQuery("select f from Favorite f", Favorite.class)
                .getResultList();
    }
}
