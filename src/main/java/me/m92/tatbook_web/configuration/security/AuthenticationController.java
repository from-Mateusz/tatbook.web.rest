package me.m92.tatbook_web.configuration.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @PostMapping
    @SuppressWarnings("unchecked")
    private void authenticate() {
        Authentication authentication = SecurityContextHolder.getContext()
                                                                .getAuthentication();
        if(null != authentication) {
            AuthenticatedUserProfile authenticatedUserProfile = (AuthenticatedUserProfile) authentication.getPrincipal();
            System.out.println("Authenticated user profile id: " + authenticatedUserProfile.getId());
        }
    }
}
