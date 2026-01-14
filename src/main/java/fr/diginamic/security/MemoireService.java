package fr.diginamic.security;

import fr.diginamic.entites.Role;
import fr.diginamic.entites.Utilisateur;
import fr.diginamic.repositories.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class MemoireService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public @NullMarked UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Utilisateur> utilisateursDB = utilisateurRepository.findAll();
        for (Utilisateur utilisateur : utilisateursDB) {
            if (Objects.equals(utilisateur.getUsername(), username)) {

                return utilisateur;

            }
        }

        throw new UsernameNotFoundException("Ce username n'existe pas.");

    }

    @Transactional
    @PostConstruct
    public void chargerUtilisateurs() {

        if (Objects.requireNonNull(utilisateurRepository.findAll()).isEmpty()) {

            utilisateurRepository.save(new Utilisateur("admin", encoder.encode("1234"), new Role("ROLE_ADMIN")));
            utilisateurRepository.save(new Utilisateur("user", encoder.encode("ouioui"), new Role("ROLE_USER")));

        }

    }

}
