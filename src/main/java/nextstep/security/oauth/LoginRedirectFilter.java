package nextstep.security.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class LoginRedirectFilter extends GenericFilterBean {

    private static String REDIRECT_URI = "https://github.com/login/oauth/authorize" +
            "?client_id={클라이언트ID}" +
            "&response_type=code" +
            "&scope=read:user" +
            "&redirect_uri=http://localhost:8080/login/oauth2/code/github";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        if (requestURI.matches("/oauth2/authorization/github")) {
            ((HttpServletResponse) response).sendRedirect(REDIRECT_URI);
            return;
        }

        chain.doFilter(request, response);
    }
}
