package it.epicode.gestione_eventi.dto;

import it.epicode.gestione_eventi.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String username;
    private String password;
    private Set<Role> roles;
}