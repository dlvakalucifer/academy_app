package dev.wegner.academy_app.student;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Student> findAll()
    {
        return repository.findAll();
    }
}
