package dev.wegner.academy_app.user_management;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Übersetzung von Meiner Userverwaltung zu Spring Security
 */
@Service
public class AcademyUserDetailsService implements UserDetailsService {

    private final AcademyUserRepository academyUserRepository;

    public AcademyUserDetailsService(AcademyUserRepository academyUserRepository) {
        this.academyUserRepository = academyUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AcademyUser academyUser = academyUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return User.builder().username(academyUser.getUsername()).password(academyUser.getPasswordHash()).disabled(!academyUser.isEnabled()).authorities(List.of(new SimpleGrantedAuthority("ROLE_" + academyUser.getRole().name()))).build();
    }
}