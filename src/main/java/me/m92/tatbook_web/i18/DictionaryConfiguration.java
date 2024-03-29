package me.m92.tatbook_web.i18;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class DictionaryConfiguration {

     private final static String DEFAULT_CHARSET = "UTF-8";

    @Bean
    @Qualifier("EmailDict")
    public EmailDictionary emailDictionary() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messaging/content/emails");
        messageSource.setDefaultEncoding(DEFAULT_CHARSET);
        messageSource.setFallbackToSystemLocale(false);
        return new EmailDictionary(messageSource);
    }

    @Bean
    @Qualifier("TextDict")
    public TextDictionary textDictionary() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messaging/content/texts");
        messageSource.setDefaultEncoding(DEFAULT_CHARSET);
        messageSource.setFallbackToSystemLocale(false);
        return new TextDictionary(messageSource);
    }

    @Bean
    @Qualifier("ErrorsDict")
    public ErrorDictionary errorDictionary() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:validations/errors");
        messageSource.setDefaultEncoding(DEFAULT_CHARSET);
        messageSource.setFallbackToSystemLocale(false);
        return new ErrorDictionary(messageSource);
    }

    @Bean
    @Qualifier("SuccessDict")
    public SuccessDictionary successDictionary() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:others/success_feedback");
        messageSource.setDefaultEncoding(DEFAULT_CHARSET);
        messageSource.setFallbackToSystemLocale(false);
        return new SuccessDictionary(messageSource);
    }
}
