package me.m92.tatbook_web.communication.mail.configuration;

import me.m92.tatbook_web.i18.MessageDictionary;
import me.m92.tatbook_web.i18.MessageDictionaryCatalog;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
class EmailTemplatingConfiguration implements ApplicationContextAware {

    private static final String CHARACTER_ENCODING = "UTF-8", TEMPLATES_PREFIX = "classpath:messaging/templates/";

    private ApplicationContext applicationContext;

    private MessageDictionary messageDictionary;

    @Autowired
    public EmailTemplatingConfiguration(@Qualifier("EmailDict") MessageDictionary messageDictionary) {
        this.messageDictionary = messageDictionary;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ISpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setTemplateEngineMessageSource(messageDictionary.getMessageSource());
        return templateEngine;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix(TEMPLATES_PREFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(CHARACTER_ENCODING);
        return templateResolver;
    }
}
