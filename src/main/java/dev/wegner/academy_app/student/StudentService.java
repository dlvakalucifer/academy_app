package dev.wegner.academy_app.student;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentService
{
    private final StudentRepository repository;

    public StudentService( StudentRepository repository )
    {
        this.repository = repository;
    }

    @Cacheable("students")
    public Page<Student> findAll( Pageable pageable )
    {
        return repository.findAll(pageable);
    }

    public Student findById(Long id)
    {
        return repository.findById(id)
                .orElseThrow();
    }

    public Student findByEmail(String email)
    {
        return repository.findByEmail(email)
                .orElseThrow();
    }

    public Student create(CreateStudentRequest request)
    {
        return repository.save(
                Student.create(
                        request.firstName(),
                        request.lastName(),
                        request.email()));
    }

    public void delete(Long id)
    {
        repository.deleteById(id);
    }
}
