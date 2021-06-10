package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public class TattooSessionDueDate extends DueDate<PaidBooking> {

    private TattooSessionDueDate() {
        super();
    }

    private TattooSessionDueDate(LocalDateTime startDate, LocalDateTime endDate, TattooProject tattooProject) {
        super(startDate, endDate, tattooProject);
    }

    public static TattooSessionDueDate create(LocalDateTime startDate, LocalDateTime endDate, TattooProject tattooProject) {
        return new TattooSessionDueDate(startDate, endDate, tattooProject);
    }
}
