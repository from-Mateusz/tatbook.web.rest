package me.m92.tatbook_web.security.jwt;

import com.google.gson.Gson;
import me.m92.tatbook_web.security.PersonalProfileAuthentication;
import me.m92.tatbook_web.security.utils.PersonalProfileAuthenticationCache;
import me.m92.tatbook_web.security.utils.PersonalProfileAuthenticationRegistry;
import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.profile.PersonalProfileManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.stream.Collectors;

public class JWTAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private JWTManager jwtManager;

    private PersonalProfileAuthenticationCache authenticationCache;

    private PersonalProfileManager<PersonalProfile> personalProfileManager;


    public JWTAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
        AssociatedTokenPair tokenPair = (new Gson()).fromJson(requestWrapper.getReader(), AssociatedTokenPair.class);

        Optional<PersonalProfileAuthentication> possibleSecurityContextPersonalProfileAuthentication = PersonalProfileAuthenticationRegistry.getAuthenticated();
        if(possibleSecurityContextPersonalProfileAuthentication.isPresent()) {
            PersonalProfileAuthentication personalProfileAuthentication = possibleSecurityContextPersonalProfileAuthentication.get();
            JWTAuthentication jwtAuthentication = personalProfileAuthentication.getJwtAuthentication();
            return handleJwtAuthentication(jwtAuthentication, tokenPair);
        }

        Optional<PersonalProfileAuthentication> possibleCachedPersonalProfileAuthentication = authenticationCache.findById(tokenPair.getId());
        if(possibleCachedPersonalProfileAuthentication.isPresent()) {
            PersonalProfileAuthentication personalProfileAuthentication = possibleCachedPersonalProfileAuthentication.get();
            JWTAuthentication jwtAuthentication = personalProfileAuthentication.getJwtAuthentication();
            return handleJwtAuthentication(jwtAuthentication, tokenPair);
        }

        return null;
    }

    private Authentication handleJwtAuthentication(JWTAuthentication jwtAuthentication, AssociatedTokenPair tokenPair) {
        if(jwtAuthentication.getAccessToken().equals(tokenPair.getAccessToken())
                && jwtAuthentication.getRefreshToken().equals(tokenPair.getRefreshToken())) {
            Optional<PersonalProfile> possiblePersonalProfile = personalProfileManager.searchById(tokenPair.getId());
            if(possiblePersonalProfile.isPresent()) {
                PersonalProfile personalProfile = possiblePersonalProfile.get();
                switch (jwtManager.verifyJWT(personalProfile, tokenPair.getAccessToken())) {
                    case CORRECT: {
                        return PersonalProfileAuthentication.create(personalProfile.getId(), jwtAuthentication);
                    }
                    case RETIRED: {
                        String newAccessToken = jwtManager.generateJWT(personalProfile, JWTManager.TokenGoal.ACCESS);
                        String newRefreshToken = jwtManager.generateJWT(personalProfile, JWTManager.TokenGoal.REFRESH);
                        return PersonalProfileAuthentication.create(personalProfile.getId(),
                                JWTAuthentication.authenticated(TokenPair.create(newAccessToken, newRefreshToken),
                                        personalProfile.getRoles().stream()
                                                .map(role -> new SimpleGrantedAuthority(role))
                                                .collect(Collectors.toList())));
                    }
                    case TAMPERED: {
                        throw new BadCredentialsException("Authorization error!");
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        HttpServletResponseWrapper httpServletResponseWrapper = new HttpServletResponseWrapper(response);
        PersonalProfileAuthentication personalProfileAuthentication = (PersonalProfileAuthentication) authResult;
        chain.doFilter(request, response);
        if(personalProfileAuthentication.hasBeenRefreshed()) {
            authenticationCache.cache(personalProfileAuthentication);
            PrintWriter writer = httpServletResponseWrapper.getWriter();
            writer.write(jsonTokenPair(personalProfileAuthentication));
        }
    }

    private String jsonTokenPair(PersonalProfileAuthentication authentication) {
        TokenPair tokenPair = authentication.getJwtTokens();
        Gson gson = new Gson();
        return gson.toJson(tokenPair);
    }

    @Override
    public void afterPropertiesSet() {
        return;
    }
}
