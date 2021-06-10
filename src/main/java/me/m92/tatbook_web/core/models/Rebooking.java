package me.m92.tatbook_web.core.models;

public abstract class Rebooking<Book extends Booking, Date extends DueDate> {

    private Book currentBooking;

    private Date proposedDueDate;

    private Boolean confirmed;

    public Rebooking(Book currentBooking, Date proposedDueDate) {
        this.currentBooking = currentBooking;
        this.proposedDueDate = proposedDueDate;
    }

    public Book getCurrentBooking() {
        return currentBooking;
    }

    public void setCurrentBooking(Book currentBooking) {
        this.currentBooking = currentBooking;
    }

    public Date getProposedDueDate() {
        return proposedDueDate;
    }

    public void setProposedDueDate(Date proposedDueDate) {
        this.proposedDueDate = proposedDueDate;
    }

    public Boolean isConfirmed() {
        return confirmed;
    }
}
