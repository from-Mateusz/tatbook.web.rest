package me.m92.tatbook_web.core.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "administrator_profile")
@DiscriminatorValue("administrator")
public class AdministratorProfile extends PersonalProfile {

    private String pin;

    @OneToMany(mappedBy = "acceptingAdministrator", cascade = CascadeType.ALL)
    private List<TattooistProfileAcceptance> tattooistProfileAcceptances;

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
