package com.graduate2.project.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@DynamicUpdate // entity 업데이트시  원하는 데이터만 업데이트 하기 위함.
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username; // 로그인한 사용자 이름

    @Column(name = "email", nullable = false)
    private String email; // 로그인한 사용자 이메일

    @Column(name = "provider", nullable = false)
    private String provider; // 사용자가 로그인한 서비스 ( google, naver, kakao )

    public User updateUser(String username, String email){
        this.username = username;
        this.email = email;

        return this;
    } // 사용자 이름이나 이메일 업데이트 하는 메소드

}
