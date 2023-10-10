package com.graduate2.project.dto;

import com.graduate2.project.domain.User;
import lombok.Getter;

import java.io.Serializable;

/*
 직렬화 기능을 가진 User클래스
 */
@Getter
public class UserDto implements Serializable {
    private String name; // 사용자 이름
    private String email; // 사용자 이메일
    private String picture;

    public UserDto(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}