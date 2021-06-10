package me.m92.tatbook_web.configuration.security.jwt;

import com.google.gson.Gson;
import me.m92.tatbook_web.api.user.authentication.Credentials;
import me.m92.tatbook_web.configuration.security.AuthenticatedUserProfileHolder;
import me.m92.tatbook_web.configuration.security.CurrentProfileAuthenticationWrapper;
import me.m92.tatbook_web.configuration.security.PersonalProfileAuthentication;
import me.m92.tatbook_web.configuration.security.utils.PersonalProfileAuthenticationCache;
import me.m92.tatbook_web.core.models.PersonalProfile;
import me.m92.tatbook_web.core.profile.PersonalProfileManager;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        Optional<PersonalProfileAuthentication> possibleCachedPersonalProfileAuthentication = authenticationCache.findById(tokenPair.getId());
        if(possibleCachedPersonalProfileAuthentication.isPresent()) {
            PersonalProfileAuthentication personalProfileAuthentication = possibleCachedPersonalProfileAuthentication.get();
            JWTAuthentication jwtAuthentication = personalProfileAuthentication.getJwtAuthentication();
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
                            return PersonalProfileAuthentication.create(possiblePersonalProfile.get().getId(),
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
        }
        return null;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response);
    }
}
