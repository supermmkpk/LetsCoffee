package com.graduate2.project.domain;

import javax.persistence.*;

@Entity
public class Cafename {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cafename_id")
    private Long id;

    @Column(name="cafe_name")
    private String cafename;

    //getter, setter, 생성자등 코드
}
