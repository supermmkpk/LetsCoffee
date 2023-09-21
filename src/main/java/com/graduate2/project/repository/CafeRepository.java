package com.graduate2.project.repository;

import com.graduate2.project.domain.Cafe;
import com.graduate2.project.domain.CafeEnum;
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

    public Cafe findOne(CafeEnum id) {
        return em.find(Cafe.class, id);
    }
}
