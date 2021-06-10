package me.m92.tatbook_web.core.models;

public class PaidBooking extends Booking<TattooSessionDueDate, PaidBooking> {

    private Deposit deposit;

    private PaidBooking() {
        super();
    }

    private PaidBooking(TattooSessionDueDate dueDate, ClientProfile client, Deposit deposit) {
        super(dueDate, client);
        this.deposit = deposit;
    }

    public static PaidBooking of(TattooSessionDueDate dueDate,
                                 ClientProfile clientProfile,
                                 Deposit deposit) {
        PaidBooking booking = new PaidBooking(dueDate, clientProfile, deposit);
        dueDate.changeToBooked(booking);
        return booking;
    }

    public void receiveDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public PaidBooking rebook(TattooSessionDueDate dueDate) {
        PaidBooking paidBooking = new PaidBooking(dueDate, getClientProfile(), deposit);
        paidBooking.setPreviousBooking(this);
        return paidBooking;
    }
}
