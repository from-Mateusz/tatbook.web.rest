package me.m92.tatbook_web.configuration.security;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class JwtCookieAuthenticationFilter extends CookieAuthenticationFilter {

    private RequestMatcher requestMatcher;

    public JwtCookieAuthenticationFilter(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /** If true, withhold providing authentication token cookie and header **/
        if(!shouldProvideAuthenticationHeaders(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthenticationToken authenticationToken = extractAuthenticationToken(request);

        if(null == authenticationToken) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
        responseWrapper.setStatus(HttpServletResponse.SC_OK);
        responseWrapper.addHeader("__authBearer", authenticationToken.getAccessToken().getDigest());

        Cookie cookie = new Cookie("__Secure-refreshToken", authenticationToken.getRefreshToken().getDigest());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        responseWrapper.addCookie(cookie);
    }

    private boolean shouldProvideAuthenticationHeaders(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    private AuthenticationToken extractAuthenticationToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if(null != securityContext) {
            AuthenticatedUserProfile userProfile = (AuthenticatedUserProfile) securityContext.getAuthentication().getPrincipal();
            return userProfile.getAuthenticationToken();
        }
        return null;
    }

//    private Token extractAccessToken(HttpServletRequestWrapper requestWrapper) {
//        return new Token(requestWrapper.getHeader("__authBearer"));
//    }
//
//    private Token extractRefreshToken(HttpServletRequestWrapper requestWrapper) {
//        Cookie[] requestCookies = requestWrapper.getCookies();
//        Token refreshToken = null;
//
//        for(Cookie cookie : requestCookies) {
//            if ("__refreshToken".equals(cookie.getName())) {
//                refreshToken = new Token(cookie.getValue());
//            }
//        }
//        return refreshToken;
//    }
}
