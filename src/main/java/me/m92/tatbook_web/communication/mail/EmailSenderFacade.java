package me.m92.tatbook_web.communication.mail;

import me.m92.tatbook_web.communication.mail.helpers.EmailServiceProvider;
import me.m92.tatbook_web.communication.mail.helpers.EmailSessionFactory;
import me.m92.tatbook_web.communication.mail.producers.RegistrationEmailProducer;
import me.m92.tatbook_web.i18.EmailDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailSenderFacade {

    private TemplateEngine emailTemplateEngine;

    private EmailDictionary emailDictionary;

    @Autowired
    public EmailSenderFacade(TemplateEngine emailTemplateEngine, EmailDictionary emailDictionary) {
        this.emailTemplateEngine = emailTemplateEngine;
        this.emailDictionary = emailDictionary;
    }

    public void send(EmailServiceProvider emailServiceProvider, EmailCategory category, Map<String, String> parameters) throws IOException, EmailSenderException {
        switch(emailServiceProvider) {
            case OUTLOOK -> sendViaOutlook(category, parameters);
            case GMAIL -> sendViaGmail(category, parameters);
        }
    }

    private void sendViaOutlook(EmailCategory category, Map<String, String> parameters) throws IOException, EmailSenderException {
        switch (category) {
            case CLIENT_PROFILE_REGISTRATION -> {
                RegistrationEmailProducer emailProducer = RegistrationEmailProducer.getInstance(
                                                                RegistrationEmailProducer.UserGroup.CLIENT,
                                                                EmailSessionFactory.create(EmailServiceProvider.OUTLOOK),
                                                                emailTemplateEngine,
                                                                emailDictionary);
                EmailSender.send(emailProducer.produce(parameters));
            }
        }
    }

    private void sendViaGmail(EmailCategory category, Map<String, String> parameters) throws IOException, EmailSenderException {

    }
}
