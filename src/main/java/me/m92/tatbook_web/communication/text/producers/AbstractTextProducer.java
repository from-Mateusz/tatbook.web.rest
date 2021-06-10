package me.m92.tatbook_web.communication.text.producers;

import me.m92.tatbook_web.communication.text.Text;
import me.m92.tatbook_web.i18.MessageDictionary;
import me.m92.tatbook_web.i18.utils.LocaleFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class AbstractTextProducer implements TextProducer {

    protected MessageDictionary messageDictionary;

    public AbstractTextProducer(MessageDictionary messageDictionary) {
        this.messageDictionary = messageDictionary;
    }

    @Override
    public Text produce(Map<String, String> parameters) {
        return Text.createEmpty(parameters.get("to"), parameters.get("languageCode"));
    }
}
