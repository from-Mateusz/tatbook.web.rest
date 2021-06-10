package me.m92.tatbook_web.core.project;

import me.m92.tatbook_web.core.models.*;

public interface BookingService {
    boolean book(TattooSessionDueDate tattooSessionDueDate, Deposit deposit);
    boolean book(ConsultationDueDate consultationDueDate);
    boolean requestRebook(PaidBooking currentBooking);
    boolean requestRebook(FreeOfChargeBooking currentBooking);
    boolean rebook(PaidBooking currentBooking, TattooSessionDueDate differentDueDate);
    boolean rebook(FreeOfChargeBooking freeOfChargeBooking, ConsultationDueDate differentDueDate);
}
