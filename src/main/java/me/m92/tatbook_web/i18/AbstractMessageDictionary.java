package me.m92.tatbook_web.i18;

import me.m92.tatbook_web.i18.utils.LocaleFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

abstract class AbstractMessageDictionary implements MessageDictionary {

    private MessageSourceAccessor messageSourceAccessor;

    private MessageSource messageSource;

    public AbstractMessageDictionary(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    public String findMessage(String code, String language, Object...args) {
        if(args.length > 0) {
           return messageSourceAccessor.getMessage(code, args, LocaleFactory.create(language));
        }
        return messageSourceAccessor.getMessage(code, LocaleFactory.create(language));
    }

    @Override
    public MessageSource getMessageSource() {
        return messageSource;
    }
}
