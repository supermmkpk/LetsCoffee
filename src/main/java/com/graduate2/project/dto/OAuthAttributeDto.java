package com.graduate2.project.dto;

import com.graduate2.project.domain.User;
import com.graduate2.project.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributeDto {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributeDto(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributeDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        // 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)
        // kakao
        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }
        // naver
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        //google
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributeDto ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributeDto.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .build();
    }

    private static OAuthAttributeDto ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        // JSON 형태이기 때문에 Map을 통해 데이터를 가져온다.
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributeDto.builder()
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .build();
    }
    public static OAuthAttributeDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributeDto.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER) // 기본 권한 USER
                .build();
    }
}
