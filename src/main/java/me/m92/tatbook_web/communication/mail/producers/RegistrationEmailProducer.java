package me.m92.tatbook_web.communication.mail.producers;

import me.m92.tatbook_web.communication.mail.Email;
import me.m92.tatbook_web.i18.EmailDictionary;
import me.m92.tatbook_web.i18.utils.LocaleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class RegistrationEmailProducer implements EmailProducer {

    private UserGroup userGroup;

    private Session session;

    private ITemplateEngine templateEngine;

    private EmailDictionary emailDictionary;

    public enum UserGroup {
        CLIENT, TATTOOIST
    }

    private RegistrationEmailProducer() {}

    private RegistrationEmailProducer(UserGroup userGroup,
                                     Session session,
                                     ITemplateEngine templateEngine,
                                     EmailDictionary emailDictionary) {
        this.userGroup = userGroup;
        this.session = session;
        this.templateEngine = templateEngine;
        this.emailDictionary = emailDictionary;
    }

    public static RegistrationEmailProducer getInstance(UserGroup userGroup,
                                                        Session session,
                                                        ITemplateEngine templateEngine,
                                                        EmailDictionary emailDictionary) {
        return new RegistrationEmailProducer(userGroup, session, templateEngine, emailDictionary);
    }

    @Override
    public Email produce(Map<String, String> parameters) {
        MimeMessage mimeMessage = new MimeMessage(this.session);
        IContext templateContext = new Context(LocaleFactory.create(parameters.get("languageCode")));
        final String messageSubject = emailDictionary.findMessage("email.client.welcome.subject", parameters.get("languageCode"));
        final String messageBody = templateEngine.process("client_welcoming_email_template", templateContext);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessageHelper.setTo(parameters.get("emailAddress"));
            mimeMessageHelper.addInline("logo@tatbook.io", new ClassPathResource("images/tatbook_logo.png"));

            switch (userGroup) {
                case CLIENT: {
                    mimeMessageHelper.setSubject(emailDictionary.findMessage("email.client.welcome.subject", parameters.get("languageCode")));
                    mimeMessageHelper.setText(templateEngine.process("client_welcoming_email_template", templateContext), true);
                    break;
                }
                case TATTOOIST: {
                    mimeMessageHelper.setSubject(emailDictionary.findMessage("email.tattooist.welcome.subject", parameters.get("languageCode")));
                    mimeMessageHelper.setText(templateEngine.process("tattooist_welcoming_email_template", templateContext), true);
                    break;
                }
            }

            return Email.create(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException firstEx) {
            return Email.empty();
        }
    }
}
