package it.epicode.gestione_eventi.services;

import it.epicode.gestione_eventi.entities.AppUser;
import it.epicode.gestione_eventi.repositories.AppUserRepository;
import it.epicode.gestione_eventi.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AppUser> findAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser createUser(UserDTO userDTO) {
        AppUser user = new AppUser();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(userDTO.getRoles());
        return appUserRepository.save(user);
    }
}