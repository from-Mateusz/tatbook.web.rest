package me.m92.tatbook_web.core.project;

import me.m92.tatbook_web.core.models.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository<T extends Booking> extends CrudRepository<T, Long> {
}
