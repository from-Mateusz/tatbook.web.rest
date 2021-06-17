package me.m92.tatbook_web.i18;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Qualifier("ErrorsDict")
class ErrorDictionary extends AbstractMessageDictionary {
    public ErrorDictionary(MessageSource messageSource) {
        super(messageSource);
    }
}
