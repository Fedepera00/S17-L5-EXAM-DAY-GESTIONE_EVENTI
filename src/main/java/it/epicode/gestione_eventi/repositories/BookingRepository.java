package it.epicode.gestione_eventi.repositories;

import it.epicode.gestione_eventi.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
}