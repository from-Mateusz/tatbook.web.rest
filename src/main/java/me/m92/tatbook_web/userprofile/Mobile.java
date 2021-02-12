package me.m92.tatbook_web.userprofile;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Mobile {

    @Column(name = "mobile")
    private String number;

    protected Mobile() { }

    protected Mobile(String number) {
        this.number = number;
    }

    public static Mobile of(String number) {
        return new Mobile(number);
    }

    public String getNumber() {
        return number;
    }
}
