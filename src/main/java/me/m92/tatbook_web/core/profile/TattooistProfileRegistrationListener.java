package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.communication.mail.EmailCategory;
import me.m92.tatbook_web.communication.mail.EmailSenderException;
import me.m92.tatbook_web.communication.mail.EmailSenderFacade;
import me.m92.tatbook_web.communication.mail.helpers.EmailServiceProvider;
import me.m92.tatbook_web.communication.text.TextSenderFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.Map;

@Component
public class TattooistProfileRegistrationListener {

    private TextSenderFacade textSender;

    private EmailSenderFacade emailSender;

    @TransactionalEventListener
    public void listen(ClientProfileRegistration clientProfileRegistration) {
        textSender.send(TextSenderFacade.TextCategory.MOBILE_NUMBER_VERIFICATION,
                Map.of("to", clientProfileRegistration.getParameters().get(ClientProfileRegistration.RequiredParameters.MOBILE_NUMBER),
                        "code", clientProfileRegistration.getParameters().get(ClientProfileRegistration.RequiredParameters.SMS_CODE)));
        try {
            emailSender.send(EmailServiceProvider.OUTLOOK,
                    EmailCategory.CLIENT_PROFILE_REGISTRATION,
                    Map.of("to", clientProfileRegistration.getParameters().get(ClientProfileRegistration.RequiredParameters.EMAIL_ADDRESS)));
        } catch (IOException | EmailSenderException ex) {
            //TODO: Try to send message through different email service provider
        }
    }
}
