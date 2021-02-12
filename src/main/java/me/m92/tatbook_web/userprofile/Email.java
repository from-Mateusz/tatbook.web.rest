package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.logger.ApplicationLogger;
import me.m92.tatbook_web.logger.ConsoleApplicationLogger;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
public class Email implements Serializable {

    private static final ApplicationLogger LOGGER = new ConsoleApplicationLogger(Email.class);

    private static final Pattern PATTERN = Pattern.compile("\\b[a-z0-9]+[-.]?[a-z0-9]+@{1}[a-z0-9]+\\.{1}[a-z]{2,3}\\b");

    @Column(name = "email")
    private String address;

    protected Email() { }

    protected Email(String address) {
        this.address = address;
    }

    public static Email of(String address) {
        if(!PATTERN.matcher(address).matches()) {
            String message = "Incorrect email address";
            LOGGER.error(message, address);
            throw new IllegalArgumentException(message);
        }
        return new Email(address);
    }

    public String getAddress() {
        return address;
    }
}
