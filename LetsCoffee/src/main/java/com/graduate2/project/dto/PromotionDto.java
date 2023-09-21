package com.graduate2.project.dto;

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
    private String period;
    private String store;
    //private String content;
    private List<String> content;
    private List<String> specialInfo;


}
