package dev.wegner.academy_app.storage;

import dev.wegner.academy_app.student.StudentRepository;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class StorageDocumentService {

    StorageProperties properties;
    StorageConfiguration storageConfiguration;

    private final StudentRepository studentRepository;
    private final StorageDocumentRepository documentRepository;

    public StorageDocumentService(StorageProperties properties, StorageConfiguration storageConfiguration, StudentRepository studentRepository, StorageDocumentRepository documentRepository) {
        this.properties = properties;
        this.storageConfiguration = storageConfiguration;
        this.studentRepository = studentRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public StorageDocument upload(Long studentId, MultipartFile file) throws Exception {
        var objectKey = UUID.randomUUID() + "-" + file.getOriginalFilename();
        var student = studentRepository.findById(studentId).orElseThrow();
        var document = StorageDocument.create(objectKey, file.getOriginalFilename(), file.getContentType(), file.getSize(), student);

        storageConfiguration.minioClient(properties).putObject(PutObjectArgs.builder().bucket(properties.bucket()).object(objectKey).stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());

        return documentRepository.save(document);
    }

    public InputStream download(Long documentId) throws Exception {
        var document = documentRepository.findById(documentId).orElseThrow();

        return storageConfiguration.minioClient(properties).getObject(GetObjectArgs.builder().bucket(properties.bucket()).object(document.getObjectKey()).build());
    }
}
