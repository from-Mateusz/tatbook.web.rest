package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalProfileRegistrationTests {

    @Autowired
    private RegistrationService registrationService;

    @Test
    public void shouldInjectDependencies() {
        assertThat(registrationService, notNullValue());
    }

    @Test
    public void shouldRegisterNewTattooistProfile() throws FailedOperationException {
        PersonalProfileRegistration registration = new PersonalProfileRegistration();
        registration.setEmailAddress("mateusz@gmail.com");
        registration.setMobileNumber("633000999");
        registration.setPassword("testPassword");
        registration.setName("Mateusz");
        registration.setTattooist(true);

        Feedback registrationFeedback = registrationService.register(ProjectionWrapper.of(registration, "pl"));
        assertThat(registrationFeedback.isOK(), is(true));
    }
}
