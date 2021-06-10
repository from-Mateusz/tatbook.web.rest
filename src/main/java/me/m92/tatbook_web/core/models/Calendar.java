package me.m92.tatbook_web.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calendar {

    private List<DueDate> dueDates;

    private Calendar() {
        dueDates = new ArrayList<>();
    }

    public static Calendar create() {
        return new Calendar();
    }

    public void addDueDate(DueDate dueDate) {
        this.dueDates.add(dueDate);
    }

    public void removeDueDate(DueDate dueDate) {
        this.dueDates.remove(dueDate);
    }

    public void freeDueDate(DueDate dueDate) {
        dueDates.stream()
                .filter(date -> date.equals(dueDate))
                .findFirst()
                .ifPresent(date -> date.setFree());
    }

    public void bookDueDate(DueDate dueDate, Booking booking) {

    }

    public List<DueDate> getDueDates() {
        return Collections.unmodifiableList(dueDates);
    }
}
