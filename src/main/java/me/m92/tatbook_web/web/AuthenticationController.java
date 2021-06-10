package me.m92.tatbook_web.web;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.api.common.projection.Feedback;
import me.m92.tatbook_web.api.common.projection.ProjectionFeedback;
import me.m92.tatbook_web.api.user.authentication.AuthenticationService;
import me.m92.tatbook_web.api.user.authentication.Credentials;
import me.m92.tatbook_web.api.user.authentication.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Feedback> authenticate(@RequestBody Credentials credentials) throws FailedOperationException {
        ProjectionFeedback<Token> feedback = authenticationService.authenticate(credentials);
        return ResponseEntity.status(feedback.getStatus()).body(feedback);
    }
}
