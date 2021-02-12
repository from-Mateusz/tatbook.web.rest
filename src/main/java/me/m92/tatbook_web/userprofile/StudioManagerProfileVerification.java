package me.m92.tatbook_web.userprofile;

import javax.persistence.*;

@Entity
@Table(name = "studio_manager_verification")
public class StudioManagerProfileVerification extends UserProfileVerification {

    @Id
    @OneToOne
    @JoinColumn(name = "tattooist_id")
    private StudioManagerProfile studioManagerProfile;

    public StudioManagerProfile getStudioManagerProfile() {
        return studioManagerProfile;
    }

    public void setStudioManagerProfile(StudioManagerProfile studioManagerProfile) {
        this.studioManagerProfile = studioManagerProfile;
    }
}
