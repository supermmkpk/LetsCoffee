package com.web.letscoffee.repository;

import com.web.letscoffee.domain.CafeId;
import com.web.letscoffee.domain.Menu;
import com.web.letscoffee.domain.MenuType;
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

    /**
     * cafe 테이블과 menu 테이블 조인
     * 넘어온 CafeId와 MenuType에 적합한 메뉴 리스트 반환
     */
    public List<Menu> findByCafeAndType(CafeId id, MenuType type) {
        return em.createQuery("select m from Menu m INNER JOIN m.cafe c where c.id = :id and m.type = :type", Menu.class)
                .setParameter("id", id)
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
