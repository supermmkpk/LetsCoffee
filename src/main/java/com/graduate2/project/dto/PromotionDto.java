package com.graduate2.project.dto;

import com.graduate2.project.domain.PromotionType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Builder
@ToString
public class PromotionDto {
    private String name;
    private PromotionType type;
    //private String image;
    private String period;
    private String store;

    private List<String> content;

}
