package dev.wegner.academy_app.student;

import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;
    private final StudentService service;

    public StudentController(StudentRepository repository, StudentService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<StudentResponse> findAll() {
        return service.findAll().stream().map(student -> toResponse(student)).toList();
    }

    @GetMapping("/by-email")
    public StudentResponse findByEmail(@RequestParam String email) {
        var student = repository.findByEmail(email).orElseThrow();

        return toResponse(student);
    }

    @GetMapping("/{id}")
    public StudentResponse findById(@PathVariable Long id) {
        var student = repository.findById(id).orElseThrow();
        return toResponse(student);
    }

    @CacheEvict(value = "students", allEntries = true)
    @PostMapping
    public StudentResponse create(@Valid @RequestBody CreateStudentRequest request) {
        var student = repository.save(Student.create(request.firstName(), request.lastName(), request.email()));
        return toResponse(student);
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail());
    }
}