package me.m92.tatbook_web.web;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.ProjectionWrapper;
import me.m92.tatbook_web.api.user.registration.PersonalProfileRegistration;
import me.m92.tatbook_web.api.user.registration.RegistrationService;
import me.m92.tatbook_web.web.utils.FeedbackResponseEntityPreparer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tattooist")
public class TattooistProfileController {

    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<Feedback> register(HttpServletRequest req, @RequestBody PersonalProfileRegistration registration) throws FailedOperationException {
        String lang = req.getParameter("lang");
        Feedback feedback = registrationService.register(ProjectionWrapper.of(registration, lang));
        return FeedbackResponseEntityPreparer.prepare(feedback);
    }
}
