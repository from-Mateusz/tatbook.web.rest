package me.m92.tatbook_web.core.models;

import me.m92.tatbook_web.configuration.security.tokens.MobileNumberConfirmationToken;
import me.m92.tatbook_web.configuration.security.tokens.PasswordResetToken;
import me.m92.tatbook_web.configuration.security.tokens.Token;
import me.m92.tatbook_web.infrastructure.converters.BooleanIntegerConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "personal_profile")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "profile_type")
public abstract class PersonalProfile {

    @TableGenerator(name = "PersonalProfileIdGenerator",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_value",
            pkColumnValue = "personal_profile_id_gen",
            initialValue = 5000,
            allocationSize = 99)
    @Id
    @GeneratedValue(generator = "PersonalProfileIdGenerator")
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

    @Convert(converter = BooleanIntegerConverter.class)
    private boolean verified;

//    private PersonalProfileVerification personalProfileVerification;

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

    public void receiveMobileNumberConfirmationToken(MobileNumberConfirmationToken token) {
        mobileNumber.receiveFreshToken(token);
    }

    public boolean confirmMobileNumber(MobileNumberConfirmationToken token) {
        if(mobileNumber.confirm(token)) {
            this.verified = true;
        }
        return this.verified;
    }

    public Password getPassword() {
        return password;
    }

    public void changePassword(String password) {
        this.password = Password.create(password);
    }

    public void receivePasswordResetToken(PasswordResetToken token) {
        this.password.setResetToken(token);
    }

    public void resetPassword(String newPassword, PasswordResetToken token) {
        this.password.reset(token, newPassword);
    }

    public List<String> getRoles() {
        return Collections.unmodifiableList(this.roles);
    }
}
