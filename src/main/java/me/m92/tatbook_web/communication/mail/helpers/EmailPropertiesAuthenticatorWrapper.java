package me.m92.tatbook_web.communication.mail.helpers;

import javax.mail.Authenticator;
import java.util.Properties;

public class EmailPropertiesAuthenticatorWrapper {

    private Properties properties;

    private Authenticator authenticator;

    public EmailPropertiesAuthenticatorWrapper(Properties properties, Authenticator authenticator) {
        this.properties = properties;
        this.authenticator = authenticator;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
}
