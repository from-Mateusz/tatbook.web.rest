package me.m92.tatbook_web.core.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PersonalProfileVerification {

    @Column(name = "verified")
    protected boolean result;

    protected PersonalProfileVerification() {}

    protected PersonalProfileVerification(boolean result) {
        this.result = result;
    }

    public static PersonalProfileVerification undone() {
        return new PersonalProfileVerification(false);
    }

    public static PersonalProfileVerification done() {
        return new PersonalProfileVerification(true);
    }

    public boolean isCorrect() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
