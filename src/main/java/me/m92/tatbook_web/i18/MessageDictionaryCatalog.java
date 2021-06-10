package me.m92.tatbook_web.i18;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageDictionaryCatalog {

    private EmailDictionary emailDictionary;

    private TextDictionary textDictionary;

    private ErrorDictionary errorDictionary;

    public enum Category {
        EMAIL, TEXT, ERROR
    }

    @Autowired
    public MessageDictionaryCatalog(EmailDictionary emailDictionary, TextDictionary textDictionary, ErrorDictionary errorDictionary) {
        this.emailDictionary = emailDictionary;
        this.textDictionary = textDictionary;
        this.errorDictionary = errorDictionary;
    }

    public MessageDictionary getByCategory(Category category) {
        switch (category) {
            case EMAIL: return this.emailDictionary;
            case TEXT: return this.textDictionary;
            case ERROR: return this.errorDictionary;
            default: break;
        }
        return null;
    }
}
