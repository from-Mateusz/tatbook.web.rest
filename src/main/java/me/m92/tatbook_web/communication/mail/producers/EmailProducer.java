package me.m92.tatbook_web.communication.mail.producers;

import me.m92.tatbook_web.communication.mail.Email;

import java.util.Map;

public interface EmailProducer {
    Email produce(Map<String, String> parameters);
}
