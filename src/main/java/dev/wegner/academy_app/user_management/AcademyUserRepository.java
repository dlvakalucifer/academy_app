package dev.wegner.academy_app.user_management;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademyUserRepository extends JpaRepository<AcademyUser, Long> {

    Optional<AcademyUser> findByUsername(String username);
}