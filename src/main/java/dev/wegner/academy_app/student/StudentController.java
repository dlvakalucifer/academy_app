package dev.wegner.academy_app.student;

import dev.wegner.academy_app.logging.LogCategories;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private final StudentService service;
    private final StudentMetrics metrics;


    public StudentController( StudentService service, StudentMetrics metrics )
    {
        this.service = service;
        this.metrics = metrics;
    }

    @GetMapping
    public Page<StudentResponse> findAll( Pageable pageable )
    {
        return service.findAll(pageable)
                .map(this::toResponse);
    }

    @GetMapping("/{id}")
    public StudentResponse findById( @PathVariable Long id )
    {
        return toResponse(service.findById(id));
    }

    @GetMapping("/by-email")
    public StudentResponse findByEmail( @RequestParam String email )
    {
        return toResponse(service.findByEmail(email));
    }

    @PostMapping
    @CacheEvict(value = "students", allEntries = true)
    public StudentResponse create( @Valid @RequestBody CreateStudentRequest request )
    {
        var student = service.create(request);
        metrics.studentCreated();
        LogCategories.STUDENT.info("Student created: {}", student);


        return toResponse(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "students", allEntries = true)
    public void delete( @PathVariable Long id )
    {
        service.delete(id);
    }

    private StudentResponse toResponse( Student student )
    {
        return new StudentResponse(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail());
    }
}