package me.m92.tatbook_web.communication.mail.helpers;

import javax.mail.Session;
import java.io.IOException;

public class EmailSessionFactory {

    public static Session create(EmailServiceProvider provider) throws IOException {
        var session = switch (provider) {
            case OUTLOOK -> createOutlookSession();
            case GMAIL -> createGmailSession();
            default -> null;
        };
        return session;
    }

    private static Session createOutlookSession() throws IOException {
        EmailPropertiesAuthenticatorWrapper emailPropertiesAuthenticatorWrapper =
                EmailPropertiesAuthenticatorFactory.create(EmailServiceProvider.OUTLOOK);
        Session session = Session.getInstance(emailPropertiesAuthenticatorWrapper.getProperties(),
                emailPropertiesAuthenticatorWrapper.getAuthenticator());
        return session;
    }

    private static Session createGmailSession() throws IOException {
        return null;
    }
}
