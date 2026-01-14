package fr.diginamic.security;

import fr.diginamic.entites.Role;
import fr.diginamic.entites.Utilisateur;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemoireService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public @NullMarked UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("admin")) {

            return new Utilisateur("admin", encoder.encode("1234"), new Role("ROLE_ADMIN"));

        } else if (username.equals("user")) {

            return new Utilisateur("user", encoder.encode("ouioui"), new Role("ROLE_USER"));

        } else {

            throw new UsernameNotFoundException("L'utilisateur n'existe pas.");

        }

    }

}
