package dev.wegner.academy_app.user_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class AcademyUserSeeder implements CommandLineRunner
{
    private final AcademyUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AcademyUserSeeder( AcademyUserRepository repository, PasswordEncoder passwordEncoder )
    {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run( String... args )
    {
        createUserIfMissing("admin", "academy", AcademyRole.ADMIN);
        createUserIfMissing("lecturer", "academy", AcademyRole.LECTURER);
        createUserIfMissing("student", "academy", AcademyRole.STUDENT);
    }

    private void createUserIfMissing( String username, String password, AcademyRole role )
    {
        if (repository.findByUsername(username)
                .isPresent())
        {
            return;
        }

        var user = AcademyUser.create(username, passwordEncoder.encode(password), true, role);
        repository.save(user);
    }
}