package me.m92.tatbook_web.core.user_profile_maintenance.user_profile_registration;

import me.m92.tatbook_web.core.location_searching.LocationSearcher;
import me.m92.tatbook_web.core.models.*;
import me.m92.tatbook_web.core.user_profile_maintenance.CustomerProfileRegistrationService;
import me.m92.tatbook_web.core.user_profile_maintenance.TattooistProfileRegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
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

    private CustomerProfileRegistrationService customerProfileRegistrationService;

    private TattooistProfileRegistrationService tattooistProfileRegistrationService;

    private LocationSearcher locationSearcher;

    @Autowired
    public void setup(CustomerProfileRegistrationService customerProfileRegistrationService,
                      TattooistProfileRegistrationService tattooistProfileRegistrationService,
                      LocationSearcher locationSearcher) {
        this.customerProfileRegistrationService = customerProfileRegistrationService;
        this.tattooistProfileRegistrationService = tattooistProfileRegistrationService;
        this.locationSearcher = locationSearcher;
    }

    @Test
    public void shouldLoadDependencies() {
        assertThat(customerProfileRegistrationService, notNullValue());
    }

    @Test
    public void shouldRegisterCustomerProfile() {
        CustomerProfile customerProfile = CustomerProfile.of("MateuszCz",
                                                                "Mateusz",
                                                                Email.of("mateo.czyzewski@gmail.com"),
                                                                "helloWorld",
                                                                MobileNumber.of("531747013"));
        boolean registered = customerProfileRegistrationService.register(customerProfile);
        assertThat(registered, is(true));
    }

    @Test
    @Commit
    public void shouldRegisterTattooistProfile() {
        Optional<Country> possibleTattooStudioCountry = locationSearcher.searchCountryById(1L);
        Optional<City> possibleTattooStudioCity = locationSearcher.searchCityById(2L);
        TattooStudio tattooStudio = TattooStudio.of("oczy",
                TattooStudioAddress.of(
                        possibleTattooStudioCountry.get(),
                        possibleTattooStudioCity.get(),
                        "Wisła 20"),
                InstagramConnection.of("oczyStudio"),
                null);

        TattooistProfile tattooistProfile = TattooistProfile.of( "Mateusz",
                "helloWorld",
                Email.of("mateo.czyzewski@gmail.com"),
                "helloWorld",
                MobileNumber.of("531799012"),
                InstagramConnection.of("mati"),
                List.of(tattooStudio));
        boolean registered = tattooistProfileRegistrationService.register(tattooistProfile);
        assertThat(registered, is(true));
    }
}
