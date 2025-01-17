package it.epicode.gestione_eventi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {

    @NotBlank(message = "Il titolo è obbligatorio")
    @Schema(description = "Titolo dell'evento", example = "Concerto Geolier", required = true)
    private String title;

    @NotBlank(message = "La descrizione è obbligatoria")
    @Schema(description = "Descrizione dell'evento", example = "Un grande concerto al Maradona", required = true)
    private String description;

    @NotNull(message = "La data è obbligatoria")
    @Future(message = "La data deve essere futura")
    @Schema(description = "Data dell'evento", example = "2025-12-31", required = true)
    private LocalDate date;

    @NotBlank(message = "La posizione è obbligatoria")
    @Schema(description = "Luogo dell'evento", example = "Stadio Maradona", required = true)
    private String location;

    @Positive(message = "Il numero di posti disponibili deve essere positivo")
    @Schema(description = "Numero di posti disponibili", example = "500", required = true)
    private int availableSeats;

    @NotNull(message = "L'ID dell'organizzatore è obbligatorio")
    @Positive(message = "L'ID dell'organizzatore deve essere un numero positivo")
    @Schema(description = "ID dell'organizzatore dell'evento", example = "1", required = true)
    private Long organizerId;
}