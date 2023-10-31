package com.graduate2.project.repository;

import com.graduate2.project.domain.Favorite;

/*
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

 */

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Favorite findByTitle(String title);
}