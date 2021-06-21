package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.configuration.security.tokens.Token;
import me.m92.tatbook_web.infrastructure.converters.BooleanIntegerConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "domain_personal_profile")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "profile_type")
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

    private List<Token> tokens;

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

    public void receiveMobileNumberToken(Token token) {
        mobileNumber.rotateToken(token);
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
}
