package me.m92.tatbook_web.core.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrator_profile")
@DiscriminatorValue("administrator")
public class AdministratorProfile extends PersonalProfile {

    private String pin;

    public AdministratorProfile(String name, EmailAddress emailAddress, MobileNumber mobileNumber, Password password, String pin) {
        super(name, emailAddress, mobileNumber, password);
        this.pin = pin;
    }

    public void accept(TattooistProfile tattooistProfile) {
        tattooistProfile.receiveAcceptance();
    }

    public void reject(TattooistProfile tattooistProfile) {
        tattooistProfile.receiveRejection();
    }
}
