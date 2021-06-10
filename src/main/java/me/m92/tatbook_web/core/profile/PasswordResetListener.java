package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.communication.mail.EmailCategory;
import me.m92.tatbook_web.communication.mail.EmailSenderException;
import me.m92.tatbook_web.communication.mail.EmailSenderFacade;
import me.m92.tatbook_web.communication.mail.helpers.EmailServiceProvider;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.Map;

public class PasswordResetListener {

    private EmailSenderFacade emailSenderFacade;

    @TransactionalEventListener
    public void listen(PasswordReset passwordReset) {
        try {
            emailSenderFacade.send(EmailServiceProvider.OUTLOOK,
                    EmailCategory.PASSWORD_RESET,
                    Map.of("to", passwordReset.getParameters().get(PasswordReset.RequiredParameters.EMAIL_ADDRESS)));
        } catch (IOException | EmailSenderException ex) {
            //TODO: Try to send message through different email service provider
        }
    }
}
