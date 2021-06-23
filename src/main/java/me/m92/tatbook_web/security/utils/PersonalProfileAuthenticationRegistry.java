package me.m92.tatbook_web.security.utils;

import me.m92.tatbook_web.security.PersonalProfileAuthentication;
import me.m92.tatbook_web.security.jwt.JWTAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class PersonalProfileAuthenticationRegistry {
    public static PersonalProfileAuthentication register(Long id, JWTAuthentication jwtAuthentication) {
        PersonalProfileAuthentication personalProfileAuthentication =
                    PersonalProfileAuthentication.create(id, jwtAuthentication);
        SecurityContextHolder.getContext()
                                .setAuthentication(jwtAuthentication);
        return personalProfileAuthentication;
    }

    public static Optional<PersonalProfileAuthentication> getAuthenticated() {
        return Optional.ofNullable(
                    (PersonalProfileAuthentication) SecurityContextHolder.getContext()
                    .getAuthentication());
    }
}
