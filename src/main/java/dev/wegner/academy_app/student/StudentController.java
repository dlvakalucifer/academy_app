package dev.wegner.academy_app.student;

import dev.wegner.academy_app.logging.LogCategories;
import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.model.registry.PrometheusRegistry;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private final StudentRepository repository;
    private final StudentService service;

    private final Counter createdCounter;

    public StudentController( StudentRepository repository, StudentService service,  PrometheusRegistry meterRegistry )
    {
        this.repository = repository;
        this.service = service;

        this.createdCounter = Counter.builder()
                .name("academy.students.build")
                .help("Academy Student Created Counter")
                .register(meterRegistry);
    }

    @GetMapping
    public List<StudentResponse> findAll()
    {
        return service.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/by-email")
    public StudentResponse findByEmail( @RequestParam String email )
    {
        var student = repository.findByEmail(email)
                .orElseThrow();

        return toResponse(student);
    }

    @GetMapping("/{id}")
    public StudentResponse findById( @PathVariable Long id )
    {
        var student = repository.findById(id)
                .orElseThrow();
        return toResponse(student);
    }

    @CacheEvict(value = "students", allEntries = true)
    @PostMapping
    public StudentResponse create( @Valid @RequestBody CreateStudentRequest request )
    {
        var student = repository.save(Student.create(request.firstName(), request.lastName(), request.email()));
        LogCategories.STUDENT.info("Student created: {}", student);

        createdCounter.inc();

        return toResponse(student);
    }

    private StudentResponse toResponse( Student student )
    {
        return new StudentResponse(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail());
    }
}