package it.epicode.gestione_eventi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.gestione_eventi.dto.EventDTO;
import it.epicode.gestione_eventi.entities.Event;
import it.epicode.gestione_eventi.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Controller", description = "Gestione degli eventi")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Visualizza tutti gli eventi", description = "Permette agli admin e agli organizzatori di visualizzare tutti gli eventi.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Visualizza evento per ID", description = "Permette agli admin e agli organizzatori di visualizzare i dettagli di un evento specifico.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<Event> getEventById(
            @PathVariable @Schema(description = "ID dell'evento da visualizzare", example = "1") Long id
    ) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo evento", description = "Permette agli admin e agli organizzatori di creare un nuovo evento.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<Event> createEvent(
            @RequestBody @Valid EventDTO eventDTO
    ) {
        return ResponseEntity.status(201).body(eventService.createEvent(eventDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna un evento", description = "Permette agli admin e agli organizzatori di aggiornare un evento esistente.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<Event> updateEvent(
            @PathVariable @Schema(description = "ID dell'evento da aggiornare", example = "1") Long id,
            @RequestBody @Valid EventDTO eventDTO
    ) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un evento", description = "Permette agli admin e agli organizzatori di eliminare un evento.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable @Schema(description = "ID dell'evento da eliminare", example = "1") Long id
    ) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}