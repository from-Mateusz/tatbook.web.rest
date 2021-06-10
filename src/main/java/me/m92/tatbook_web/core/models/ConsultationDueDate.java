package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;

public class ConsultationDueDate extends DueDate<FreeOfChargeBooking> {

    private ConsultationDueDate() {
        super();
    }

    private ConsultationDueDate(LocalDateTime startDate, LocalDateTime endDate, TattooProject tattooProject) {
        super(startDate, endDate, tattooProject);
    }

    public static ConsultationDueDate create(LocalDateTime startDate, TattooProject tattooProject) {
        return new ConsultationDueDate(startDate, null, tattooProject);
    }

    public static ConsultationDueDate create(LocalDateTime startDate, LocalDateTime endDate, TattooProject tattooProject) {
        return new ConsultationDueDate(startDate, endDate, tattooProject);
    }
}
