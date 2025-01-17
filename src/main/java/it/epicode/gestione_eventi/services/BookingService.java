package it.epicode.gestione_eventi.services;

import it.epicode.gestione_eventi.entities.AppUser;
import it.epicode.gestione_eventi.repositories.AppUserRepository;
import it.epicode.gestione_eventi.enums.Role;
import it.epicode.gestione_eventi.entities.Booking;
import it.epicode.gestione_eventi.entities.Event;
import it.epicode.gestione_eventi.exceptions.ValidationException;
import it.epicode.gestione_eventi.repositories.BookingRepository;
import it.epicode.gestione_eventi.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Long userId, Long eventId, int seatsBooked) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ValidationException("Evento non trovato."));

        if (event.getAvailableSeats() < seatsBooked) {
            throw new ValidationException("Posti non disponibili.");
        }

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("Utente non trovato."));

        boolean isAlreadyBooked = bookingRepository.existsByUserIdAndEventId(userId, eventId);
        if (isAlreadyBooked) {
            throw new ValidationException("Hai già una prenotazione per questo evento.");
        }

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);
        booking.setSeatsBooked(seatsBooked);

        event.setAvailableSeats(event.getAvailableSeats() - seatsBooked);
        eventRepository.save(event);

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForUser(Long userId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ValidationException("Utente autenticato non trovato."));

        if (!currentUser.getId().equals(userId) && !currentUser.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new ValidationException("Accesso negato: non puoi visualizzare le prenotazioni di un altro utente.");
        }

        return bookingRepository.findByUserId(userId);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ValidationException("Prenotazione non trovata."));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ValidationException("Utente autenticato non trovato."));


        if (!booking.getUser().getId().equals(currentUser.getId()) && !currentUser.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new ValidationException("Accesso negato: non puoi cancellare una prenotazione di un altro utente.");
        }

        Event event = booking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + booking.getSeatsBooked());
        eventRepository.save(event);

        bookingRepository.delete(booking);
    }
}