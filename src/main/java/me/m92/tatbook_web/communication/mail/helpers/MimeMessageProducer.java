package me.m92.tatbook_web.communication.mail.helpers;

import javax.mail.internet.MimeMessage;
import java.util.Map;

public interface MimeMessageProducer {
    MimeMessage produce(Map<String, String> arguments) throws Exception;
}
