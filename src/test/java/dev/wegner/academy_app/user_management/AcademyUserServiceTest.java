package dev.wegner.academy_app.user_management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcademyUserServiceTest {

    @Mock
    private AcademyUserRepository academyUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AcademyUserService academyUserService;

    @Test
    void shouldCreateUser() {

        when(passwordEncoder.encode("academy")).thenReturn("hashedPassword");

        AcademyUser savedUser = AcademyUser.create("admin", "hashedPassword", true, AcademyRole.ADMIN);

        when(academyUserRepository.save(any())).thenReturn(savedUser);

        AcademyUser result = academyUserService.createUser("admin", "academy", AcademyRole.ADMIN);

        assertThat(result.getUsername()).isEqualTo("admin");

        assertThat(result.getPasswordHash()).isEqualTo("hashedPassword");

        verify(passwordEncoder).encode("academy");

        verify(academyUserRepository).save(any());


        //duplicate
        when(academyUserRepository.findByUsername("admin")).thenReturn(Optional.of(savedUser));

        assertThatThrownBy(() -> {
            academyUserService.createUser("admin", "academy", AcademyRole.ADMIN);
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Username already exists");
    }

    @Test
    void shouldFindUserByUsername() {

        AcademyUser user = AcademyUser.create("admin", "hash", true, AcademyRole.ADMIN);

        when(academyUserRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        AcademyUser result = academyUserService.findByUsername("admin");

        assertThat(result.getUsername()).isEqualTo("admin");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(academyUserRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> academyUserService.findByUsername("unknown")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("User not found");
    }
}