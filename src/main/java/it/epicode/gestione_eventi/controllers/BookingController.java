package it.epicode.gestione_eventi.controllers;

import it.epicode.gestione_eventi.dto.BookingDTO;
import it.epicode.gestione_eventi.entities.Booking;
import it.epicode.gestione_eventi.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking Controller", description = "Gestione delle prenotazioni")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    @Operation(summary = "Visualizza tutte le prenotazioni", description = "Permette agli admin di visualizzare tutte le prenotazioni.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @PostMapping
    @Operation(summary = "Crea una prenotazione", description = "Permette agli utenti di creare una prenotazione.")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Booking> createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        Booking booking = bookingService.createBooking(
                bookingDTO.getUserId(),
                bookingDTO.getEventId(),
                bookingDTO.getSeatsBooked()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Visualizza prenotazioni utente", description = "Permette agli utenti di visualizzare solo le proprie prenotazioni.")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsForUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Annulla una prenotazione", description = "Permette agli utenti di annullare solo le proprie prenotazioni.")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}