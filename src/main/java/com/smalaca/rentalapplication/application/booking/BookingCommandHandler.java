package com.smalaca.rentalapplication.application.booking;

import com.smalaca.rentalapplication.domain.booking.Booking;
import com.smalaca.rentalapplication.domain.booking.BookingEventsPublisher;
import com.smalaca.rentalapplication.domain.booking.BookingRepository;
import com.smalaca.rentalapplication.domain.clock.Clock;
import com.smalaca.rentalapplication.domain.event.EventIdFactory;
import com.smalaca.rentalapplication.domain.eventchannel.EventChannel;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCommandHandler {
    private final BookingRepository bookingRepository;
    private final EventChannel eventChannel;

    public BookingCommandHandler(BookingRepository bookingRepository, EventChannel eventChannel) {
        this.bookingRepository = bookingRepository;
        this.eventChannel = eventChannel;
    }

    @EventListener
    public void reject(BookingReject bookingReject) {
        Booking booking = bookingRepository.findById(bookingReject.getBookingId());

        booking.reject();

        bookingRepository.save(booking);
    }

    @EventListener
    public void accept(BookingAccept bookingAccept) {
        Booking booking = bookingRepository.findById(bookingAccept.getId());
        BookingEventsPublisher bookingEventsPublisher = new BookingEventsPublisher(new EventIdFactory(), new Clock(), eventChannel);

        booking.accept(bookingEventsPublisher);

        bookingRepository.save(booking);
    }
}
