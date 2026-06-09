package dev.wegner.academy_app.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateStudentRequest(@NotBlank String firstName, @NotBlank String lastName, @Email String email) {
}