package com.graduate2.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Favorite {
    @Id
    @Column(name="cafename_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cafe_name")
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }
}
