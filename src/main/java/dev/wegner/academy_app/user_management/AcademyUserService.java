package dev.wegner.academy_app.user_management;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AcademyUserService
{
    private final AcademyUserRepository academyUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AcademyUserService( AcademyUserRepository academyUserRepository, PasswordEncoder passwordEncoder )
    {
        this.academyUserRepository = academyUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AcademyUser createUser( String username, String password, AcademyRole role )
    {
        if (academyUserRepository.findByUsername(username)
                .isPresent())
        {
            throw new IllegalArgumentException("Username already exists");
        }

        var passwordHash = passwordEncoder.encode(password);
        var user = AcademyUser.create(username, passwordHash, true, role);

        return academyUserRepository.save(user);
    }

    public AcademyUser findByUsername( String username )
    {
        return academyUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }
}