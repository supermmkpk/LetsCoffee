package com.web.letscoffee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MenuDto {
    private String name;
    private String image;

}


