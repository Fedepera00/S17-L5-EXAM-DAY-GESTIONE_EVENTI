package it.epicode.gestione_eventi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.gestione_eventi.entities.AppUser;
import it.epicode.gestione_eventi.dto.UserDTO;
import it.epicode.gestione_eventi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Gestione degli utenti")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Visualizza tutti gli utenti", description = "Permette agli admin di visualizzare tutti gli utenti.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo utente", description = "Permette agli admin di creare un nuovo utente.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppUser> createUser(
            @RequestBody @Valid UserDTO userDTO
    ) {
        AppUser user = userService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }
}