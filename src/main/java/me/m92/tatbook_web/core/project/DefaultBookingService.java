package me.m92.tatbook_web.core.project;

import me.m92.tatbook_web.core.helpers.AuthenticatedUserProfileExtractor;
import me.m92.tatbook_web.core.models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DefaultBookingService implements BookingService {

    private AuthenticatedUserProfileExtractor authenticatedUserProfileExtractor;

    private BookingRepository<PaidBooking> paidBookingRepository;

    @Override
    public boolean book(TattooSessionDueDate tattooSessionDueDate, Deposit deposit) {
        Optional<ClientProfile> possibleClientProfile = getCurrentClientProfile();
        if(possibleClientProfile.isPresent()) {
            PaidBooking paidBooking = PaidBooking.of(tattooSessionDueDate, possibleClientProfile.get(), deposit);
            return true;
        }
        return false;
    }

    @Override
    public boolean book(ConsultationDueDate consultationDueDate) {
        Optional<ClientProfile> possibleClientProfile = getCurrentClientProfile();
        if(possibleClientProfile.isPresent()) {
            FreeOfChargeBooking freeOfChargeBooking = FreeOfChargeBooking.create(consultationDueDate,
                                                                                    possibleClientProfile.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean requestRebook(PaidBooking currentBooking) {
        Optional<PaidBooking> possibleExistingCurrentBooking = paidBookingRepository.findById(currentBooking.getId());
        if(possibleExistingCurrentBooking.isPresent()) {
            PaidBooking paidBooking = possibleExistingCurrentBooking.get();
            paidBooking.setPostponing(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean requestRebook(FreeOfChargeBooking currentBooking) {
        return false;
    }

    @Override
    public boolean rebook(PaidBooking currentBooking, TattooSessionDueDate differentDueDate) {
        return false;
    }

    @Override
    public boolean rebook(FreeOfChargeBooking freeOfChargeBooking, ConsultationDueDate differentDueDate) {
        return false;
    }

    private Optional<ClientProfile> getCurrentClientProfile() {
        return Optional.ofNullable(authenticatedUserProfileExtractor.extractPersonalProfile(ClientProfile.class));
    }
}
