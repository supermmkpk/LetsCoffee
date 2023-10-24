package com.graduate2.project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Favorite {
    @Id
    @Column(name="favorite_id")
    private Long id;

    @Column
    private String storeName;


}
