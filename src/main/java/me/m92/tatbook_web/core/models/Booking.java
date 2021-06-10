package me.m92.tatbook_web.core.models;

public abstract class Booking<D extends DueDate, B extends Booking> {

    private Long id;

    private D dueDate;

    private ClientProfile clientProfile;

    private B previousBooking;

    private boolean canceled;

    private boolean postponing;

    protected Booking() {}

    protected Booking(D dueDate, ClientProfile clientProfile) {
        this.dueDate = dueDate;
        this.clientProfile = clientProfile;
    }

    public Long getId() {
        return id;
    }

    public D getDueDate() {
        return dueDate;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public void setPostponing(boolean postponing) {
        this.postponing = postponing;
    }

    protected void setPreviousBooking(B booking) {
        booking.setCanceled(true);
        this.previousBooking = booking;
    }

    public abstract B rebook(D dueDate);
}
