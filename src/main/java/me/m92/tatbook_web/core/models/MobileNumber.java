package me.m92.tatbook_web.core.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class MobileNumber {

    private String number;

    @Embedded
    private MobileNumberConfirmation confirmation;

    private MobileNumber() {}

    private MobileNumber(String number, String token) {
        this.number = number;
        this.confirmation = MobileNumberConfirmation.create(token);
    }

    public static MobileNumber create(String number, String token) {
        return new MobileNumber(number, token);
    }

    public String getNumber() {
        return number;
    }

    public boolean isConfirmed() {
        return this.confirmation.isConfirmed();
    }

    public void confirm(String token) {
        confirmation.complete(token);
    }

    public void rotateToken(String token) {
        this.confirmation = MobileNumberConfirmation.create(token);
    }
}
