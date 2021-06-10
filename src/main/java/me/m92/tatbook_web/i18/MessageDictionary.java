package me.m92.tatbook_web.i18;

import org.springframework.context.MessageSource;

public interface MessageDictionary {
    String findMessage(String code, String language, Object...args);
    MessageSource getMessageSource();
}
