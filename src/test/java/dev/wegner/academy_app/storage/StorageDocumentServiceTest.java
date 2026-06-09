package dev.wegner.academy_app.storage;

import dev.wegner.academy_app.student.Student;
import dev.wegner.academy_app.student.StudentRepository;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageDocumentServiceTest
{
    @Mock
    MinioClient minioClient;

    @Mock
    StorageConfiguration configuration;

    @Mock
    StorageProperties properties;

    @Mock
    StudentRepository studentRepository;

    @Mock
    StorageDocumentRepository storageDocumentRepository;

    @InjectMocks
    StorageDocumentService service;

    @Test
    void shouldUploadDocument() throws Exception
    {
        when(configuration.minioClient(any())).thenReturn(minioClient);
        when(properties.bucket()).thenReturn("academy-test");
        when(studentRepository.findById(any())).thenReturn(Optional.of(Student.create("Stefan", "Wegner", "Stefan@Wegner.com")));

        var file = new MockMultipartFile("file", "test.txt", "text/plain", "hello".getBytes());

        service.upload(1L, file);

        verify(minioClient).putObject(any());
    }
}
