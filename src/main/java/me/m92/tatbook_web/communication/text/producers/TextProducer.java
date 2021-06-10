package me.m92.tatbook_web.communication.text.producers;

import me.m92.tatbook_web.communication.text.Text;

import java.util.Map;

public interface TextProducer {
    Text produce(Map<String, String> parameters);
}
