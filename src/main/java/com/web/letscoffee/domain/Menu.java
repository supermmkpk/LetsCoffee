package com.web.letscoffee.domain;

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

    @Column
    private String name;

    //private int price;

    @Column
    private String image;

    @Column
    @Enumerated(EnumType.STRING)
    private MenuType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cafe_id")
    private Cafe cafe;

    //==연관관계 매서드==// /* 양방향일때! */
    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getMenus().add(this);
    }
}
