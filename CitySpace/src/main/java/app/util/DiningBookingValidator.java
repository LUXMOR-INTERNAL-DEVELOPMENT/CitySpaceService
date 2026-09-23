package app.util;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import app.entity.Event;
import app.entity.TimeSlot;
import app.exception.InvalidBookingDateException;
import app.exception.InvalidGuestCountException;
import app.exception.SlotExpiredException;
import app.exception.SlotNotAvailableException;
import app.exception.VendorNotAvailableException;
//dining booking validation
@Component
public class DiningBookingValidator {

    public void validateGuestCount(int numberOfGuests) {

        if (numberOfGuests <= 0) {
            throw new InvalidGuestCountException(
                    "Number of guests must be greater than 0");
        }
    }

    public void validateVendor(Event event) {

        if (event == null || event.getVendorId() == null) {
            throw new VendorNotAvailableException(
                    "Vendor is not available for this dining event");
        }
    }

    public void validateSlot(TimeSlot slot) {

        if (slot == null) {
            throw new SlotNotAvailableException(
                    "Selected time slot is not available");
        }
    }

    public void validateBookingDate(
            String bookingDate,
            TimeSlot slot) {

        LocalDate date = LocalDate.parse(bookingDate);
        LocalDate today = LocalDate.now();

        if (date.isBefore(today)) {
            throw new InvalidBookingDateException(
                    "Booking date cannot be in the past");
        }

        if (date.equals(today)) {

            LocalTime currentTime = LocalTime.now();

            LocalTime slotEndTime =
                    LocalTime.parse(slot.getSlotEndTime());

            if (!currentTime.isBefore(slotEndTime)) {
                throw new SlotExpiredException(
                        "Selected time slot has already ended");
            }
        }
    }
}
