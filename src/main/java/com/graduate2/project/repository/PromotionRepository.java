package com.graduate2.project.repository;

import com.graduate2.project.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionRepository {
    private final EntityManager em;

    public void save(Promotion promotion) {
        if(promotion.getId() == null) {
            em.persist(promotion);
        }
        else {
            em.merge(promotion);
        }
    }

    public Promotion findOne(Long id) {
        return em.find(Promotion.class, id);
    }

    public List<Promotion> findByCafeAndType(CafeId id, PromotionType type) {
        return em.createQuery("select p from Promotion p INNER JOIN p.cafe c where c.id = :id and p.type = :type", Promotion.class)
                .setParameter("id", id)
                .setParameter("type", type)
                .getResultList();
    }

    public List<Promotion> findAll() {
         return em.createQuery("select p from Promotion p", Promotion.class)
                .getResultList();
    }

}
