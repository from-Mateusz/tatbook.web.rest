package me.m92.tatbook_web.communication.mail.helpers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.io.IOException;
import java.util.Properties;

public class EmailPropertiesAuthenticatorFactory {

    public static EmailPropertiesAuthenticatorWrapper create(EmailServiceProvider provider) throws IOException {
        var wrapper = switch (provider) {
            case OUTLOOK -> createForOutlook();
            case GMAIL -> createForGmail();
            default -> null;
        };
        return wrapper;
    }

    private static EmailPropertiesAuthenticatorWrapper createForOutlook() throws IOException {
        Resource propertiesResource = new ClassPathResource("/messaging/configuration/outlook.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(propertiesResource);

        Properties emailServerProperties = new Properties();
        emailServerProperties.setProperty("mail.smtp.from", properties.getProperty("mail.smtp.from"));
        emailServerProperties.setProperty("mail.smtp.host", properties.getProperty("mail.smtp.host"));
        emailServerProperties.setProperty("mail.smtp.port", properties.getProperty("mail.smtp.port"));
        emailServerProperties.setProperty("mail.smtp.auth", "true");
        emailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
        emailServerProperties.setProperty("mail.debug", "true");


        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.credentials.username"),
                        properties.getProperty("mail.credentials.password"));
            }
        };

        return new EmailPropertiesAuthenticatorWrapper(emailServerProperties, authenticator);
    }

    private static EmailPropertiesAuthenticatorWrapper createForGmail() throws IOException {
        return new EmailPropertiesAuthenticatorWrapper(null, null);
    }
}
