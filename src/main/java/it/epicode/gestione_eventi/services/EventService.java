package it.epicode.gestione_eventi.services;

import it.epicode.gestione_eventi.entities.AppUser;
import it.epicode.gestione_eventi.repositories.AppUserRepository;
import it.epicode.gestione_eventi.dto.EventDTO;
import it.epicode.gestione_eventi.entities.Event;
import it.epicode.gestione_eventi.exceptions.ResourceNotFoundException;
import it.epicode.gestione_eventi.exceptions.ValidationException;
import it.epicode.gestione_eventi.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato con ID: " + id));
    }

    public Event createEvent(EventDTO eventDTO) {
        AppUser loggedInUser = getLoggedInUser();

        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setAvailableSeats(eventDTO.getAvailableSeats());
        event.setOrganizer(loggedInUser); // Associa l'organizzatore
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato con ID: " + id));

        AppUser loggedInUser = getLoggedInUser();

        if (!event.getOrganizer().getId().equals(loggedInUser.getId())) {
            throw new ValidationException("Non sei autorizzato a modificare questo evento.");
        }

        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setAvailableSeats(eventDTO.getAvailableSeats());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato con ID: " + id));

        AppUser loggedInUser = getLoggedInUser();

        if (!event.getOrganizer().getId().equals(loggedInUser.getId())) {
            throw new ValidationException("Non sei autorizzato a eliminare questo evento.");
        }

        eventRepository.delete(event);
    }

    private AppUser getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utente loggato non trovato."));
    }
}