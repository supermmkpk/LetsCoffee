package com.web.letscoffee.dto;

import com.web.letscoffee.domain.Users;
import lombok.Getter;

import java.io.Serializable;

/*
 직렬화 기능을 가진 User클래스
 */
@Getter
public class UserDto implements Serializable {
    private Long id;
    private String name; // 사용자 이름
    private String email; // 사용자 이메일
    private String picture;
    private String provider;

    public UserDto(Users user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.provider = user.getProvider();
    }
}
