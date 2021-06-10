package me.m92.tatbook_web.core.models;

public class FreeOfChargeBooking extends Booking<ConsultationDueDate, FreeOfChargeBooking> {

    private FreeOfChargeBooking() {}

    private FreeOfChargeBooking(ConsultationDueDate dueDate, ClientProfile clientProfile) {
        super(dueDate, clientProfile);
    }

    public static FreeOfChargeBooking create(ConsultationDueDate dueDate,
                                             ClientProfile clientProfile) {
        FreeOfChargeBooking booking = new FreeOfChargeBooking(dueDate, clientProfile);
        dueDate.changeToBooked(booking);
        return booking;
    }

    @Override
    public FreeOfChargeBooking rebook(ConsultationDueDate dueDate) {
        return null;
    }
}
