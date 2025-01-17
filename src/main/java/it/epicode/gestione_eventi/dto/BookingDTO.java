package it.epicode.gestione_eventi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "DTO per la gestione delle prenotazioni")
public class BookingDTO {

    @NotNull(message = "L'ID dell'utente è obbligatorio")
    @Positive(message = "L'ID dell'utente deve essere un numero positivo")
    @Schema(description = "ID dell'utente che effettua la prenotazione", example = "1", required = true)
    private Long userId;

    @NotNull(message = "L'ID dell'evento è obbligatorio")
    @Positive(message = "L'ID dell'evento deve essere un numero positivo")
    @Schema(description = "ID dell'evento da prenotare", example = "2", required = true)
    private Long eventId;

    @NotNull(message = "Il numero di posti prenotati è obbligatorio")
    @Positive(message = "Il numero di posti prenotati deve essere maggiore di 0")
    @Schema(description = "Numero di posti da prenotare", example = "3", required = true)
    private int seatsBooked;
}