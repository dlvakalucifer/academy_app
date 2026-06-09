package dev.wegner.academy_app.storage;

import dev.wegner.academy_app.student.Student;
import dev.wegner.academy_app.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StorageDocumentRepositoryTest
{
    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StorageDocumentRepository documentRepository;

    @Test
    void shouldFindDocumentsByStudentId()
    {
        var student = studentRepository.save(Student.create("Stefan", "Wegner", "stefan@example.com"));

        documentRepository.save(StorageDocument.create("a", "cv.pdf", "application/pdf", 100, student));
        documentRepository.save(StorageDocument.create("a", "zeugnis.pdf", "application/pdf", 100, student));

        var documents = documentRepository.findByStudentId(student.getId());

        assertThat(documents).hasSize(2);
        assertThat(documents).extracting(StorageDocument::getFileName)
                .containsExactlyInAnyOrder("cv.pdf", "zeugnis.pdf");
    }
}

