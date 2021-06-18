package me.m92.tatbook_web.core.models;

import javax.persistence.Column;
import java.util.regex.Pattern;

public class EmailAddress {

    private static final Pattern SIMPLIFIED_CORRECT_PATTERN = Pattern.compile("\\b[a-z0-9]+[-.]?[a-z0-9]+@{1}[a-z0-9]+\\.{1}[a-z]{2,3}\\b");

    private static final Pattern HARDENED_CORRECT_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    @Column(name = "email")
    private String address;

    private EmailAddress() {}

    private EmailAddress(String address) {
        this.address = address;
    }

    public static EmailAddress of(String address) {
        if(!hasValidFormat(address)) {
            return null;
        }
        return new EmailAddress(address);
    }

    public static boolean hasValidFormat(String address) {
        return HARDENED_CORRECT_PATTERN.matcher(address).matches();
    }

    public String getAddress() {
        return address;
    }
}
