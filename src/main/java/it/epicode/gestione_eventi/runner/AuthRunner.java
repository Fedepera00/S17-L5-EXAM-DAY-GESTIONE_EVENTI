package it.epicode.gestione_eventi.runner;

import it.epicode.gestione_eventi.entities.AppUser;
import it.epicode.gestione_eventi.enums.Role;
import it.epicode.gestione_eventi.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Optional<AppUser> adminUser = appUserService.findByUsername("admin");
        if (adminUser.isEmpty()) {
            appUserService.registerUser("admin", "adminpwd", Set.of(Role.ROLE_ADMIN));
        }
        if (appUserService.findByUsername("organizer").isEmpty()) {
            appUserService.registerUser("organizer", "organizerpwd", Set.of(Role.ROLE_ORGANIZER));
        }

        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user", "userpwd", Set.of(Role.ROLE_USER));
        }
    }
}
