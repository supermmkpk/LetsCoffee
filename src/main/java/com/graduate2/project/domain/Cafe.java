package com.graduate2.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cafe {
    @Id
    @Column(name="cafe_id")
    @Enumerated(EnumType.STRING)
    private CafeId id;

    @OneToMany(mappedBy="cafe")
    private List<Promotion> promotions = new ArrayList<>();

    @OneToMany(mappedBy="cafe")
    private List<Menu> menus = new ArrayList<>();
}
