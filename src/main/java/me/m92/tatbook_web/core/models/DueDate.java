package me.m92.tatbook_web.core.models;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class DueDate<B extends Booking> {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private TattooProject tattooProject;

    private B booking;

    private State state;

    public enum State {
        BOOKED, FREE
    }

    protected DueDate() {}

    protected DueDate(LocalDateTime startDate, LocalDateTime endDate, TattooProject tattooProject) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.tattooProject = tattooProject;
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

    public TattooProject getTattooProject() {
        return tattooProject;
    }

    public void setTattooProject(TattooProject tattooProject) {
        this.tattooProject = tattooProject;
    }

    public void changeToBooked(B booking) {
        if(state == State.BOOKED) {
            throw new UnsupportedOperationException("Already booked due date");
        }
        this.state = State.BOOKED;
        this.booking = booking;
    }

    public void setFree() {
        this.state = State.FREE;
        this.booking = null;
    }

    public boolean isFree() {
        return state == State.FREE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DueDate dueDate = (DueDate) o;
        return Objects.equals(startDate, dueDate.startDate) &&
                Objects.equals(endDate, dueDate.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}


