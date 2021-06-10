package me.m92.tatbook_web.communication.mail;

import javax.mail.internet.MimeMessage;

public class Email {

    private MimeMessage message;

    public Email(MimeMessage message) {
        this.message = message;
    }

    public static Email create(MimeMessage message) {
        return new Email(message);
    }

    public static Email empty() {
        return new Email(null);
    }

    public MimeMessage getMessage() {
        return message;
    }

    public boolean isEmpty() {
        return null == message;
    }
}
