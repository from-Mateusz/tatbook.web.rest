package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.converters.BooleanToIntegerConverter;
import me.m92.tatbook_web.converters.LocalDateTimeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class UserProfileVerification implements Serializable {

    public static final long VERIFICATION_HOURS = 72;

    @Column(name = "start_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime startDate;

    @Column(name = "expire_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime expireDate;

    @Column(name = "verify_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime verifyDate;

    @Column(name = "rejected")
    @Convert(converter = BooleanToIntegerConverter.class)
    private boolean rejected;

    @ManyToOne
    @JoinColumn(name = "administrator_id")
    private AdministratorProfile administratorProfile;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    protected UserProfileVerification() { }

    protected UserProfileVerification(LocalDateTime startDate, LocalDateTime expireDate) {
        this.startDate = startDate;
        this.expireDate = expireDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public LocalDateTime getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(LocalDateTime verifyDate) {
        this.verifyDate = verifyDate;
    }

    public AdministratorProfile getAdministratorProfile() {
        return administratorProfile;
    }

    public void setAdministratorProfile(AdministratorProfile administratorProfile) {
        this.administratorProfile = administratorProfile;
    }

    public boolean isVerified() {
        return null != verifyDate;
    }

    public boolean isRejected() {
        return rejected;
    }
}
