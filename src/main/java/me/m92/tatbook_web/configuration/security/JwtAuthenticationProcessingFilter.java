package me.m92.tatbook_web.configuration.security;

import me.m92.tatbook_web.configuration.security.tokens.Token;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private JwtTokenService jwtTokenService;

    public JwtAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                             JwtTokenService jwtTokenService) {
        super(requiresAuthenticationRequestMatcher);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) request);

        Token accessToken = extractAccessToken(requestWrapper);
        Token refreshToken = extractRefreshToken(requestWrapper);
        Optional<AuthenticatedUserProfile> possibleTokenOwner = jwtTokenService.findOwner(accessToken, refreshToken);

        Authentication authentication = null;
        if(!possibleTokenOwner.isPresent()) {
            if(jwtTokenService.canRefreshToken(refreshToken)) {
                jwtTokenService.blacklistToken(accessToken);
                Token refreshedAccessToken = jwtTokenService.refreshAccessToken(refreshToken);
                authentication = new UsernamePasswordAuthenticationToken(refreshedAccessToken, refreshToken);
            }
            else {
                throw new BadCredentialsException("Incorrect authentication credentials");
            }
        }
        else {
            if(jwtTokenService.shouldRefreshToken(accessToken)) {
                jwtTokenService.blacklistToken(accessToken);
                Token refreshedAccessToken = jwtTokenService.refreshAccessToken(refreshToken);
                authentication = new UsernamePasswordAuthenticationToken(refreshedAccessToken, refreshToken);
            }
            else {
                authentication = new UsernamePasswordAuthenticationToken(accessToken, refreshToken);
            }
        }

        return getAuthenticationManager().authenticate(authentication);
    }

    private Token extractAccessToken(HttpServletRequestWrapper requestWrapper) {
        return new Token(requestWrapper.getHeader("__authBearer"));
    }

    private Token extractRefreshToken(HttpServletRequestWrapper requestWrapper) {
        Cookie[] requestCookies = requestWrapper.getCookies();
        Token refreshToken = null;

        for(Cookie cookie : requestCookies) {
            if ("__Secure-refreshToken".equals(cookie.getName())) {
                refreshToken = new Token(cookie.getValue());
            }
        }
        return refreshToken;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
