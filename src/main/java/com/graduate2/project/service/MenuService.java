package com.graduate2.project.service;

import com.graduate2.project.domain.CafeId;
import com.graduate2.project.domain.Menu;
import com.graduate2.project.domain.MenuType;
import com.graduate2.project.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    @Transactional
    public Long save(Menu menu) {
        menuRepository.save(menu);
        return menu.getId();
    }

    public List<Menu> findByCafeAndType(CafeId id, MenuType type) {
        return menuRepository.findByCafeAndType(id, type);
    }

    public Menu findOne(Long id) {
        return menuRepository.findOne(id);
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
