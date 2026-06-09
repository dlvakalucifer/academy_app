package dev.wegner.academy_app.user_management;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcademyUserRepositoryTest {

    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    private AcademyUserRepository repository;

    @Test
    void shouldFindUserByUsername() {

        AcademyUser user = AcademyUser.create("admin", "hash", true, AcademyRole.ADMIN);

        repository.save(user);

        Optional<AcademyUser> result = repository.findByUsername("admin");

        assertThat(result).isPresent();

        assertThat(result.get().getRole()).isEqualTo(AcademyRole.ADMIN);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {

        Optional<AcademyUser> result = repository.findByUsername("unknown");

        assertThat(result).isEmpty();
    }
}