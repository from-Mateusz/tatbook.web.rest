package me.m92.tatbook_web.core.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "client_profile")
@DiscriminatorValue("client")
@PrimaryKeyJoinColumn(name = "client_profile_id")
public class ClientProfile extends PersonalProfile {

    @Transient
    private List<TattooStyle> favoriteStyles;

    private ClientProfile() {
        super();
    }

    private ClientProfile(String name, EmailAddress emailAddress, MobileNumber mobileNumber, Password password) {
        super(name, emailAddress, mobileNumber, password);
        favoriteStyles = new ArrayList<>();
    }

    public static ClientProfile create(String name, EmailAddress emailAddress, MobileNumber mobileNumber, Password password) {
        return new ClientProfile(name, emailAddress, mobileNumber, password);
    }

    public void addFavoriteStyle(TattooStyle style) {
        this.favoriteStyles.add(style);
    }

    public void removeFavoriteStyle(TattooStyle style) {
        this.favoriteStyles.remove(style);
    }

    @Override
    public List<String> getRoles() {
        if(this.roles.isEmpty()) {
            this.roles.add("tattooist");
        }
        return Collections.unmodifiableList(this.roles);
    }
}
