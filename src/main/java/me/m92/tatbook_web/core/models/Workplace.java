package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public class Workplace {

    private TattooistProfile tattooistProfile;

    private TattooStudio tattooStudio;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Workplace() {}

    private Workplace(TattooistProfile tattooistProfile,
                      TattooStudio tattooStudio,
                      LocalDateTime startDate,
                      LocalDateTime endDate) {
        this.tattooistProfile = tattooistProfile;
        this.tattooStudio = tattooStudio;
        this.startDate = startDate;
    }

    public static Workplace create(TattooistProfile tattooistProfile,
                                   TattooStudio tattooStudio) {
        return new Workplace(tattooistProfile, tattooStudio, null, null);
    }

    public static Workplace createGuestSpot(TattooistProfile tattooistProfile,
                                            TattooStudio tattooStudio,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate) {
        return new Workplace(tattooistProfile, tattooStudio, startDate, endDate);
    }

    public TattooistProfile getTattooistProfile() {
        return tattooistProfile;
    }

    public TattooStudio getTattooStudio() {
        return tattooStudio;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
