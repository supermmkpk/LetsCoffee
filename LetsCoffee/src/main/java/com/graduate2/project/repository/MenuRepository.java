package com.graduate2.project.repository;

import com.graduate2.project.domain.Menu;
import com.graduate2.project.domain.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuRepository {
    private final EntityManager em;

    public void save(Menu menu) {
        if(menu.getId() == null) {
            em.persist(menu);
        }
        else {
            em.merge(menu);
        }
    }

    public List<Menu> findByType(MenuType type) {
       return em.createQuery("select m from Menu m where m.type = :type", Menu.class)
                .setParameter("type", type)
                .getResultList();
    }

    public Menu findOne(Long id) {
        return em.find(Menu.class, id);
    }

    public List<Menu> findAll() {
        return em.createQuery("select m from Menu m", Menu.class)
                .getResultList();
    }
}
