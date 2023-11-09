package com.web.letscoffee.dto;

import com.web.letscoffee.domain.PromotionType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PromotionDto {
    private String name;
    private PromotionType type;
    //private String image;
    private String period;
    private String store;

    private String[] content;

}
