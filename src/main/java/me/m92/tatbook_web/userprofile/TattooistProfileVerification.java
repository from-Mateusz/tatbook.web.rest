package me.m92.tatbook_web.userprofile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tattooist_verification")
public class TattooistProfileVerification extends UserProfileVerification {

    @Id
    @OneToOne
    @JoinColumn(name = "tattooist_id")
    private TattooistProfile tattooistProfile;

    protected TattooistProfileVerification() {}

    protected TattooistProfileVerification(LocalDateTime startDate, LocalDateTime expireDate, TattooistProfile tattooistProfile) {
        super(startDate, expireDate);
        this.tattooistProfile = tattooistProfile;
    }

    public static TattooistProfileVerification of(TattooistProfile tattooistProfile) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime expireDate = currentDate.plusHours(UserProfileVerification.VERIFICATION_HOURS);
        return new TattooistProfileVerification(currentDate, expireDate, tattooistProfile);
    }

    public TattooistProfile getTattooistProfile() {
        return tattooistProfile;
    }

    public void setTattooistProfile(TattooistProfile tattooistProfile) {
        this.tattooistProfile = tattooistProfile;
    }
}
