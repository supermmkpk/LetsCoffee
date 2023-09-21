package com.graduate2.project.domain;

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

    private String name;

    private String period;

    private String Store;

    @Column(length = 2000)
    private String content;

    @Column(length = 2000)
    private String specialInfo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cafe_id")
    private Cafe cafe;

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
        cafe.getPromotions().add(this);
    }
}
