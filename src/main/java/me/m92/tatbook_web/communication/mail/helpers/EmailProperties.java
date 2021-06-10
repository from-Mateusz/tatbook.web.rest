package me.m92.tatbook_web.communication.mail.helpers;

import java.util.Properties;

public abstract class EmailProperties {

    private String from;

    private String host;

    private String port;

    private String auth;

    private String starttlsEnabled;

    public EmailProperties(String from, String host, String port, String auth, String starttlsEnabled) {
        this.from = from;
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.starttlsEnabled = starttlsEnabled;
    }

    abstract Properties getProperties();
}
