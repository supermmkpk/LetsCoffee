package com.graduate2.project.SecurityConfig;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectURL = request.getParameter("redirectURL");

        if (redirectURL != null && !redirectURL.isEmpty()) {
            // redirectURL을 사용하여 리디렉션 또는 다른 작업 수행
            getRedirectStrategy().sendRedirect(request, response, redirectURL);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
