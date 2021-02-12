package me.m92.tatbook_web.userprofile.service;

import me.m92.tatbook_web.projection.ApplicationResponse;

public interface UserProfileService {
    ApplicationResponse searchForTattooistProfile(Long id);
    ApplicationResponse searchForClientProfile(Long id);
}
