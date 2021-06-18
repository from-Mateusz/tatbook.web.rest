package me.m92.tatbook_web.i18;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Qualifier("SuccessDict")
public class SuccessDictionary extends AbstractMessageDictionary {
    public SuccessDictionary(MessageSource messageSource) {
        super(messageSource);
    }
}
