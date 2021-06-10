package me.m92.tatbook_web.communication.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.StringJoiner;

import static java.net.http.HttpRequest.newBuilder;

@Service
class SmsPlanetTextSender implements TextSender {

    private final Logger logger = LoggerFactory.getLogger(SmsPlanetTextSender.class);

    private final HttpClient httpClient;

    private Properties apiProperties;

    private boolean enabled;

    public SmsPlanetTextSender(@Value("${texting.enabled}") boolean enabled) {
        this.httpClient = HttpClient.newHttpClient();
        this.enabled = enabled;
    }

    @PostConstruct
    private void initApiProperties() throws Exception {
        Resource apiPropertiesResource = new ClassPathResource("/messaging/configuration/smsplanet.properties");
        this.apiProperties = PropertiesLoaderUtils.loadProperties(apiPropertiesResource);
    }

    @Override
    public void send(Text message) {
        if(enabled) {
            this.httpClient.sendAsync(createApiRequest(message.getTo(), message.getContent()), HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(result -> logger.info("Response from sms sending web service: " + result));
        }
    }

    private HttpRequest createApiRequest(String mobileNumber, String content) {
        StringJoiner requestBody = new StringJoiner("&");
        requestBody.add("key=" + apiProperties.getProperty("key"))
                .add("password=" + apiProperties.getProperty("password"))
                .add("from=" + apiProperties.getProperty("addressee"))
                .add("to=" + mobileNumber)
                .add("msg=" + content);

        HttpRequest request = newBuilder(URI.create(apiProperties.getProperty("endpoint")))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        return request;
    }
}
