package me.m92.tatbook_web.core.models;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tattooist_profile_acceptance")
public class TattooistProfileAcceptance {

    private static final long DEFAULT_ACCEPTANCE_TIME = (3 * (60 * 24));

    @TableGenerator(name = "TattooistAcceptanceIdGenerator",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_value",
            pkColumnValue = "tattooist_profile_acceptance_id_gen",
            initialValue = 2500,
            allocationSize = 99)
    @Id
    @GeneratedValue(generator = "TattooistAcceptanceIdGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrator_profile_id")
    private AdministratorProfile acceptingAdministrator;

    @Column(name = "acceptance_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime acceptanceDate;

    @Column(name = "rejection_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime rejectionDate;

    @Column(name = "acceptance_expire_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime expireDate;

    private TattooistProfileAcceptance() {}

    private TattooistProfileAcceptance(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public static TattooistProfileAcceptance create() {
        LocalDateTime currentTime = LocalDateTime.now();
        return new TattooistProfileAcceptance(currentTime.plusMinutes(DEFAULT_ACCEPTANCE_TIME));
    }

    public static TattooistProfileAcceptance create(LocalDateTime expireDate) {
        return new TattooistProfileAcceptance(expireDate);
    }

    void accept() {
        if(!canAcceptOrReject()) {
            throw new UnsupportedOperationException();
        }
        this.acceptanceDate = LocalDateTime.now();
    }

    void reject() {
        if(!canAcceptOrReject()) {
            throw new UnsupportedOperationException();
        }
        this.rejectionDate = LocalDateTime.now();
    }

    private boolean canAcceptOrReject() {
        LocalDateTime currentDate = LocalDateTime.now();
        if(null == rejectionDate && null == acceptanceDate && currentDate.isBefore(expireDate)) {
            return true;
        }
        return false;
    }

    public boolean isAccepted() {
        return null != acceptanceDate;
    }

    public boolean isRejected() {
        return null != rejectionDate;
    }
}
