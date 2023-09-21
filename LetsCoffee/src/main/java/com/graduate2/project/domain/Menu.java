package com.graduate2.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue
    @Column(name="menu_id")
    private Long id;

    private String name;

    //private int price;

    private String image;

    @Enumerated(EnumType.STRING)
    private MenuType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cafe_id")
    private Cafe cafe;

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getMenus().add(this);
    }
}
