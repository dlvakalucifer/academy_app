package dev.wegner.academy_app.user_management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcademyUserDetailsServiceTest {

    @Mock
    private AcademyUserRepository repository;

    @InjectMocks
    private AcademyUserDetailsService service;

    @Test
    void shouldLoadUser() {

        AcademyUser academyUser = AcademyUser.create("admin", "hash", true, AcademyRole.ADMIN);

        when(repository.findByUsername("admin")).thenReturn(Optional.of(academyUser));

        UserDetails result = service.loadUserByUsername("admin");

        assertThat(result.getUsername()).isEqualTo("admin");

        assertThat(result.getPassword()).isEqualTo("hash");

        assertThat(result.getAuthorities()).hasSize(1);

        assertThat(result.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");
        //ODER
        assertThat(result.getAuthorities()).anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        //ODER
        assertThat(result.getAuthorities()).map(GrantedAuthority::getAuthority).containsExactly("ROLE_ADMIN");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(repository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername("unknown")).isInstanceOf(UsernameNotFoundException.class);
    }
}

