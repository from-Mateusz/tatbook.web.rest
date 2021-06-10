package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.api.common.FailedOperationException;
import me.m92.tatbook_web.infrastructure.converters.BooleanIntegerConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "domain_personal_profile")
public class PersonalProfile {

    private Long id;

    private String name;

    @Embedded
    private EmailAddress emailAddress;

    @Embedded
    private MobileNumber mobileNumber;

    @Embedded
    private Password password;

    @Convert(converter = BooleanIntegerConverter.class)
    private boolean blocked;

    private PersonalProfileVerification personalProfileVerification;

    @Transient
    protected List<String> roles = new ArrayList<>();

    protected PersonalProfile() {}

    protected PersonalProfile(String name,
                              EmailAddress emailAddress,
                              MobileNumber mobileNumber,
                              Password password) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress.getAddress();
    }

    public void changeEmailAddress(String emailAddress) {
        this.emailAddress = EmailAddress.of(emailAddress);
    }

    public String getMobileNumber() {
        return mobileNumber.getNumber();
    }

    public void changeMobileNumber(MobileNumber mobileNumber) {
        this.mobileNumber = mobileNumber;

    }

    public void confirmMobileNumber(String token) {
        mobileNumber.confirm(token);
    }

    public boolean hasConfirmedMobileNumber() {
        return mobileNumber.isConfirmed();
    }

    public Password getPassword() {
        return password;
    }

    public void changePassword(String password) {
        this.password = Password.create(password);
    }

    public void setPasswordResetToken(String token) {
        this.password.setResetToken(token);
    }

    public void resetPassword(String newPassword, String token) {
        this.password.reset(newPassword, token);
    }

    public List<String> getRoles() {
        return Collections.unmodifiableList(this.roles);
    }

    public static void main(String...args) {
        PersonalProfile personalProfile = new PersonalProfile("Mateusz Czyzewski",
                                                                EmailAddress.of("mateo.czyzewski@gmail.com"),
                                                                MobileNumber.create("500990300", "dasdasq3e32432432423"),
                                                                Password.create("mateuszXXX"));
        personalProfile.setPasswordResetToken("start54321");
        personalProfile.resetPassword("testPassword", "st333t54321");
    }
}
