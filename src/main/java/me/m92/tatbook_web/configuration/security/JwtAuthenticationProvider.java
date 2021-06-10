package me.m92.tatbook_web.configuration.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private JwtAuthenticationService jwtAuthenticationService;

    public JwtAuthenticationProvider(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return;
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return jwtAuthenticationService.findOwnerByTokens((Token) authentication.getPrincipal(), (Token) authentication.getCredentials())
                                        .orElseThrow(() -> new BadCredentialsException("Incorrect authentication credentials"));
    }
}
