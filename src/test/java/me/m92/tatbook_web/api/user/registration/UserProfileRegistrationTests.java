package me.m92.tatbook_web.api.user.registration;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.user.registration.projection.BasicProfileData;
import me.m92.tatbook_web.api.user.registration.projection.CustomerProfileData;
import me.m92.tatbook_web.api.user.registration.projection.TattooistProfileData;
import me.m92.tatbook_web.api.user.registration.projection.TattooistWorkplaceData;
import me.m92.tatbook_web.core.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserProfileRegistrationTests {

    private CustomerRegistrationService customerRegistrationService;

    private TattooistRegistrationService tattooistRegistrationService;

    @Autowired
    public void setup(CustomerRegistrationService customerRegistrationService,
                      TattooistRegistrationService tattooistRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
        this.tattooistRegistrationService = tattooistRegistrationService;
    }

    @Test
    public void shouldCompleteSetup() {
        assertThat(customerRegistrationService, notNullValue());
        assertThat(tattooistRegistrationService, notNullValue());
    }

    @Test
    public void shouldRegisterCustomerProfile() throws FailedOperationException {
        CustomerProfileData customerProfileData = new CustomerProfileData();
        BasicProfileData basicProfileData = new BasicProfileData();
        basicProfileData.setEmail("m92@gmail.com");
        basicProfileData.setUsername("mati");
        basicProfileData.setMobileNumber("543999012");
        basicProfileData.setName("Mateuszzz");
        basicProfileData.setPassword("test999");
        basicProfileData.setLanguage("pl");
        customerProfileData.setBasicProfileData(basicProfileData);

        Feedback afterRegistrationFeedback = customerRegistrationService.register(customerProfileData);
        assertThat(afterRegistrationFeedback.isOK(), is(true));
    }

    @Test
    public void shouldRegisterTattooistProfile() throws FailedOperationException {
        TattooistProfileData tattooistProfileData = new TattooistProfileData();

        BasicProfileData basicProfileData = new BasicProfileData();
        basicProfileData.setEmail("m92@gmail.com");
        basicProfileData.setUsername("mati");
        basicProfileData.setMobileNumber("543999012");
        basicProfileData.setName("Mateuszzz");
        basicProfileData.setPassword("test999");
        basicProfileData.setLanguage("pl");

        TattooistWorkplaceData workplaceData = new TattooistWorkplaceData();
        workplaceData.setCountryId(1L);
        workplaceData.setCityId(2L);
        workplaceData.setStreet("Wolska 20");
        workplaceData.setName("Hard Studio");
        workplaceData.setInstagramProfileName("@wolska_tattoos");

        tattooistProfileData.setBasicProfileData(basicProfileData);
        tattooistProfileData.setWorkplaceData(List.of(workplaceData));
        tattooistProfileData.setInstagramProfileName("teo");

        Feedback afterRegistrationFeedback = tattooistRegistrationService.register(tattooistProfileData);
        assertThat(afterRegistrationFeedback.isOK(), is(true));
    }
}
