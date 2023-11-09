package com.web.letscoffee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Promotion {
    @Id
    @GeneratedValue
    @Column(name="promotion_id")
    private Long id;

    @Column
    private String name;
    @Column
    private String period;

    @Column
    private String image;
    @Column
    private String link;
    @Column
    private String Store;
    @Column(length = 2000)
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cafe_id")
    private Cafe cafe;

    //==연관관계 매서드==//
    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getPromotions().add(this);
    }
}
