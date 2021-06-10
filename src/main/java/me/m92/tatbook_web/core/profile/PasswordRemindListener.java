package me.m92.tatbook_web.core.profile;

import me.m92.tatbook_web.communication.mail.EmailCategory;
import me.m92.tatbook_web.communication.mail.EmailSenderException;
import me.m92.tatbook_web.communication.mail.EmailSenderFacade;
import me.m92.tatbook_web.communication.mail.helpers.EmailServiceProvider;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.Map;

public class PasswordRemindListener {

    private EmailSenderFacade emailSender;

    @TransactionalEventListener
    public void listen(PasswordRemind passwordRemind) {
        try {
            emailSender.send(EmailServiceProvider.OUTLOOK,
                    EmailCategory.PASSWORD_REMIND,
                    Map.of(
                    "to", passwordRemind.getParameters().get(PasswordRemind.RequiredParameters.EMAIL_ADDRESS),
                    "resetCode", passwordRemind.getParameters().get(PasswordRemind.RequiredParameters.RESET_CODE)));
        } catch(IOException | EmailSenderException ex) {
            //TODO: Try to send message through different email service provider
        }
    }
}
