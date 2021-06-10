package me.m92.tatbook_web.configuration.security.utils;

import me.m92.tatbook_web.configuration.security.PersonalProfileAuthentication;
import me.m92.tatbook_web.configuration.security.jwt.JWTAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class PersonalProfileAuthenticationRegistry {
    public static PersonalProfileAuthentication register(Long id, JWTAuthentication jwtAuthentication) {
        PersonalProfileAuthentication personalProfileAuthentication =
                    PersonalProfileAuthentication.create(id, jwtAuthentication);
        SecurityContextHolder.getContext()
                                .setAuthentication(jwtAuthentication);
        return personalProfileAuthentication;
    }

    public static PersonalProfileAuthentication getAuthenticated() {
        return (PersonalProfileAuthentication) SecurityContextHolder.getContext()
                                                                    .getAuthentication();
    }
}
