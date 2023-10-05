package com.graduate2.project.domain;

import com.graduate2.project.dto.UserProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
public enum OAuthAttributes {
    GOOGLE("google", (attribute) ->{
        UserProfile userProfile = new UserProfile();
        userProfile.setUserName((String)attribute.get("name"));
        userProfile.setEmail((String)attribute.get("email"));

        return userProfile;
    }),

    NAVER("naver", (attribute) -> {
        UserProfile userProfile = new UserProfile();

        Map<String, String> responseValue = (Map)attribute.get("response");

        userProfile.setUserName(responseValue.get("name"));
        userProfile.setEmail(responseValue.get("email"));

        return userProfile;
    }),

    KAKAO("kakao", (attribute) -> {

        Map<String, Object> account = (Map)attribute.get("kakao_account");
        Map<String, String> profile = (Map)account.get("profile");

        UserProfile userProfile = new UserProfile();
        userProfile.setUserName(profile.get("nickname"));
        userProfile.setEmail((String)account.get("email"));

        return userProfile;
    });

    private final String registrationId; // 로그인한 서비스
    private final Function<Map<String, Object>, UserProfile> of; // 로그인한 사용자 정보를 통해 UserProfile 가져옴.

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfile> of){
        this.registrationId = registrationId;
        this.of = of;
    }
    public static UserProfile extract(String registrationId, Map<String, Object> attributes){
        return Arrays.stream(values())
                .filter(value -> registrationId.equals(value.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
