package app.mapper;

import org.springframework.stereotype.Component;

import app.dto.DiningBookingResponse;
import app.entity.Booking;
import app.entity.Event;
import app.entity.Payment;
import app.entity.TimeSlot;


@Component
public class DiningBookingResponseMapper {
	
 public DiningBookingResponse map(Booking booking,TimeSlot slot,Payment payment,Event event) {
	DiningBookingResponse response = new DiningBookingResponse();

	response.setBookingId(booking.getBookingId());
	response.setEventName(event.getEventName());
	response.setBookingDate(booking.getBookingDate());
	response.setSlotId(booking.getSlotId());

	response.setSlotStartTime(slot.getSlotStartTime());
	response.setSlotEndTime(slot.getSlotEndTime());

	response.setNumberOfGuests(booking.getNumberOfGuests());
	response.setBookingStatus(booking.getBookingStatus());

	response.setPaymentId(payment.getPaymentId());
	response.setBookingAmount(payment.getBookingAmount());
	response.setGstAmount(payment.getGstAmount());
	response.setConvenienceFee(payment.getConvenienceFee());
	response.setTotalAmount(payment.getTotalAmount());
	response.setPaymentStatus(payment.getPaymentStatus());

	return response;
 }
}
