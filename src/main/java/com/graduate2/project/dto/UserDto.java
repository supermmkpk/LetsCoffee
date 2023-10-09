package com.graduate2.project.dto;

import com.graduate2.project.domain.User;
import lombok.Getter;

@Getter
public class UserDto {
    private String username; // 사용자 이름
    private String provider; // 로그인한 서비스
    private String email; // 사용자 이메일

    public void setUserName(String username){
        this.username = username;
    }

    public void setProvider(String provider){
        this.provider = provider;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .username(this.username)
                .email(this.email)
                .provider(this.provider)
                .build();
    }
}
