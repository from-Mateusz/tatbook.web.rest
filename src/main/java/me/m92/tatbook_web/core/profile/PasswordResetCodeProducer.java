package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.core.models.PersonalProfile;

public interface PasswordResetCodeProducer {
    PasswordResetCode produce(PersonalProfile personalProfile);
}
