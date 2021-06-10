package me.m92.tatbook_web.core.models;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@AttributeOverride(name = "verified", column = @Column(name = "widelyVerified"))
public class ExtendedPersonalProfileVerification extends PersonalProfileVerification {

    @Embedded
    private PersonalProfileVerification personalProfileVerification;

    private ExtendedPersonalProfileVerification(PersonalProfileVerification personalProfileVerification, boolean result) {
        super(result);
        this.personalProfileVerification = personalProfileVerification;
    }

    public static ExtendedPersonalProfileVerification undone(PersonalProfileVerification personalProfileVerification) {
        return new ExtendedPersonalProfileVerification(personalProfileVerification, false);
    }

    public static ExtendedPersonalProfileVerification done(PersonalProfileVerification personalProfileVerification) {
        return new ExtendedPersonalProfileVerification(personalProfileVerification, true);
    }

    @Override
    public boolean isCorrect() {
        return super.isCorrect() && this.isCorrect();
    }

    @Override
    public void setResult(boolean result) {
        this.result = result;
    }
}
