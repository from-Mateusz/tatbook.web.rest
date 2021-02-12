package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.DomainEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * In this case Bio means short description of tattooist profile, which is self-imposed
 */
@Entity
@Table(name = "tattooist_bio")
public class TattooistProfileBio extends DomainEntity implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "tattooist_id")
    private TattooistProfile tattooistProfile;

    @Embedded
    private ColorPreferences colorPreferences;

    private String pseudonym;

    @Lob
    private String about;

    public TattooistProfileBio() {}

    protected TattooistProfileBio(TattooistProfile tattooistProfile, ColorPreferences colorPreferences, String pseudonym, String about) {
        this.tattooistProfile = tattooistProfile;
        this.colorPreferences = colorPreferences;
        this.pseudonym = pseudonym;
        this.about = about;
    }

    /**
     * I benefit from Builder Pattern due to all attributes are voluntary
     * It totally depends on client's (developer) code, which attributes should be set
     */
    public static class Builder {

        private TattooistProfile tattooistProfile;

        private ColorPreferences colorPreferences;

        private String pseudonym;

        private String about;

        public Builder(TattooistProfile tattooistProfile) {
            this.tattooistProfile = tattooistProfile;
        }

        public Builder withTattooColorPreferences(ColorPreferences preferences) {
            colorPreferences = preferences;
            return this;
        }

        public Builder withPseudonym(String pseudonym) {
            this.pseudonym = pseudonym;
            return this;
        }

        public Builder withAbout(String about) {
            this.about = about;
            return this;
        }

        public TattooistProfileBio build() {
            return new TattooistProfileBio(tattooistProfile, colorPreferences, pseudonym, about);
        }
    }

    @Override
    public Long getId() {
        return tattooistProfile.getId();
    }

    public TattooistProfile getTattooistProfile() {
        return tattooistProfile;
    }

    public void setTattooistProfile(TattooistProfile tattooistProfile) {
        this.tattooistProfile = tattooistProfile;
    }

    public ColorPreferences getColorPreferences() {
        return colorPreferences;
    }

    public void setColorPreferences(ColorPreferences colorPreferences) {
        this.colorPreferences = colorPreferences;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void changePseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public void changeAbout(String about) {
        this.about = about;
    }

    public void changeTattooColorPreferences(ColorPreferences preferences) {
        colorPreferences = preferences;
    }
}
