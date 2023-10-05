package com.graduate2.project.SecurityConfig;
import com.graduate2.project.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2Service oAuth2Service;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http.csrf().disable() // csrf 보안설정 사용 x
                .formLogin().disable()// 폼 로그인 사용 x

                .oauth2Login() // OAuth2를 통한 로그인 사용
                .defaultSuccessUrl("/", true)// 로그인 성공시 이동할 URL
                .userInfoEndpoint() // 사용자가 로그인에 성공할 경우,
                .userService(oAuth2Service) // 해당 서비스 로직을 타도록 설정

                .and()
                .and().build();
    }

}
