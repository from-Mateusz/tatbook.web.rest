package me.m92.tatbook_web.configuration.security;

import com.google.gson.Gson;
import me.m92.tatbook_web.api.common.validations.ValidationFailureBundle;
import me.m92.tatbook_web.api.user.authentication.Credentials;
import me.m92.tatbook_web.api.user.authentication.CredentialsValidator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Optional;

public class PrimaryAuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher;

    private final CredentialsValidator credentialsValidator;

    private final AuthenticationService authenticationService;

    public PrimaryAuthenticationFilter(RequestMatcher requestMatcher,
                                       CredentialsValidator credentialsValidator,
                                       AuthenticationService authenticationService) {
        this.requestMatcher = requestMatcher;
        this.credentialsValidator = credentialsValidator;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(!shouldHandleRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        Gson gson = new Gson();
        Credentials credentials = gson.fromJson(new InputStreamReader(requestWrapper.getInputStream()), Credentials.class);
        ValidationFailureBundle failureBundle = credentialsValidator.validate(credentials);
        if(!failureBundle.isEmpty()) {
            filterChain.doFilter(request, response);

            responseWrapper.resetBuffer();
            OutputStream responseBody = responseWrapper.getOutputStream();
            String jsonResponseContent = gson.toJson(failureBundle);
            for(byte jsonResponseContentByte : jsonResponseContent.getBytes()) {
                responseBody.write(jsonResponseContentByte);
            }
            response.setHeader("Content-Type", "application/json");
            responseWrapper.copyBodyToResponse();
        }
        else {
            AuthenticationToken token = authenticationService.authenticate(credentials);
            Optional<AuthenticatedUserProfile> possibleAuthenticatedUserProfile =
                    authenticationService.findOwnerByTokens(token.getAccessToken(), token.getRefreshToken());
            possibleAuthenticatedUserProfile.ifPresent(authenticatedUserProfile -> {
                SecurityContextHolder.getContext()
                                        .setAuthentication(
                                                new UsernamePasswordAuthenticationToken(authenticatedUserProfile,
                                                        authenticatedUserProfile.getPassword(),
                                                        authenticatedUserProfile.getAuthorities()));
            });

            filterChain.doFilter(request, response);

            responseWrapper.addHeader("__authBearer", token.getAccessToken().getDigest());
            Cookie cookie = new Cookie("__Secure-refreshToken", token.getRefreshToken().getDigest());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            responseWrapper.addCookie(cookie);
            responseWrapper.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private boolean shouldHandleRequest(HttpServletRequest request) {
        return "POST".equals(request.getMethod().toUpperCase()) && this.requestMatcher.matches(request);
    }
}
