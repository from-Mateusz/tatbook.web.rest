package me.m92.tatbook_web.communication.text;

import me.m92.tatbook_web.communication.text.producers.VerificationCodeTextProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TextSenderFacade {

    private TextSender textSender;

    private VerificationCodeTextProducer verificationCodeMessageProducer;

    @Autowired
    public TextSenderFacade(TextSender textSender, VerificationCodeTextProducer verificationCodeMessageProducer) {
        this.textSender = textSender;
        this.verificationCodeMessageProducer = verificationCodeMessageProducer;
    }

    public enum TextCategory {
        MOBILE_NUMBER_VERIFICATION
    }

    public void send(TextCategory textCategory, Map<String, String> parameters) {
        switch (textCategory) {
            case MOBILE_NUMBER_VERIFICATION -> textSender.send(verificationCodeMessageProducer.produce(parameters));
        }
    }
}
