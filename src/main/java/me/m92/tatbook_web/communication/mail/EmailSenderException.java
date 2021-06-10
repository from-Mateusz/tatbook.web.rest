package me.m92.tatbook_web.communication.mail;

public class EmailSenderException extends Exception {
    public EmailSenderException(String message) {
        super(message);
    }

    public EmailSenderException(Throwable cause) {
        super(cause);
    }
}
