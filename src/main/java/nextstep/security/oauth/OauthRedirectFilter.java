package nextstep.security.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nextstep.security.authentication.Authentication;
import nextstep.security.authentication.UsernamePasswordAuthenticationToken;
import nextstep.security.context.HttpSessionSecurityContextRepository;
import nextstep.security.context.SecurityContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class OauthRedirectFilter extends GenericFilterBean {

    public static final String ACCESS_TOKE_URI = "http://localhost:8089/login/oauth/access_token";
    private HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest =  ((HttpServletRequest) request);
        HttpServletResponse httpServletResponse = ((HttpServletResponse) response);

        String requestURI = httpServletRequest.getRequestURI();

        if (requestURI.matches("/login/oauth2/code/github")) {
            RestTemplate restTemplate = new RestTemplate();
            final GithubOauthRequest githubOauthRequest = new GithubOauthRequest("{클라이언트ID}", "{시크릿코드}", "", "/user");

            Map<String, String> map = restTemplate.postForObject(ACCESS_TOKE_URI, githubOauthRequest, Map.class);

//            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(map.get("email"), null, Set.of());
//            SecurityContext securityContext = new SecurityContext(authentication);
//
//            repository.saveContext(securityContext, httpServletRequest, httpServletResponse);

            httpServletResponse.sendRedirect("/user");
            return;
        }
        chain.doFilter(request, httpServletResponse);
    }
}
