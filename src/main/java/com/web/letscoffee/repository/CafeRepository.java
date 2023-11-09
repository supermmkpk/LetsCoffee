package com.web.letscoffee.repository;

import com.web.letscoffee.domain.Cafe;
import com.web.letscoffee.domain.CafeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CafeRepository {
    private final EntityManager em;

    public void save(Cafe cafe) {
        em.persist(cafe);
    }

    public Cafe findOne(CafeId id) {
        return em.find(Cafe.class, id);
    }

}
