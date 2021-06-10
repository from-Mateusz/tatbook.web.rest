package me.m92.tatbook_web.communication.text.producers;

import me.m92.tatbook_web.communication.text.Text;
import me.m92.tatbook_web.i18.MessageDictionary;
import me.m92.tatbook_web.i18.TextDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VerificationCodeTextProducer extends AbstractTextProducer {

    @Autowired
    public VerificationCodeTextProducer(TextDictionary messageDictionary) {
        super(messageDictionary);
    }

    @Override
    public Text produce(Map<String, String> parameters) {
        Text text = super.produce(parameters);
        text.editContent(messageDictionary
                        .findMessage("text_mobile_verification_code", text.getLanguageCode(), parameters.get("token")));
        return text;
    }
}
