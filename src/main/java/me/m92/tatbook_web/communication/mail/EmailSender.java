package me.m92.tatbook_web.communication.mail;

import javax.mail.MessagingException;
import javax.mail.Transport;

class EmailSender {
    public static void send(Email email) throws EmailSenderException {
        if(email.isEmpty()) {
            throw new EmailSenderException("Email is not correctly build");
        }
        try {
            Transport.send(email.getMessage());
        } catch (MessagingException ex) {
            throw new EmailSenderException(ex.getCause());
        }
    }
}
