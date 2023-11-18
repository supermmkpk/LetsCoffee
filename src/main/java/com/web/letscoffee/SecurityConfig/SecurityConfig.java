package com.web.letscoffee.SecurityConfig;

import com.web.letscoffee.service.CustomOAuth2UserService;
import com.web.letscoffee.domain.Role;
import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity // Spring Security 설정 활성화
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig{

    private final CustomOAuth2UserService customOAuth2UserService;

    //@Autowired
    //private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 보안설정 사용 x
                .headers().frameOptions().disable()// h2-console 화면을 사용하기 위해 해당 옵션 disable

                .and()
                .authorizeRequests() // URL별 권한 관리
                .antMatchers("/mypage/**").authenticated() // 해당 URL은 인증 절차 수행해야함
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // /api/v1/** 은 USER권한만 접근 가능
                .anyRequest().permitAll() // 나머지 요청들은 모두 인증 절차 생략가능

                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .oauth2Login() // OAuth2를 통한 로그인 사용
                .defaultSuccessUrl("/login_success")
                .userInfoEndpoint() // 사용자가 로그인에 성공할 경우, 가져올 설정들
                //소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구조체 등록
                .userService(customOAuth2UserService); // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시

        return http.getOrBuild();
    }
}
