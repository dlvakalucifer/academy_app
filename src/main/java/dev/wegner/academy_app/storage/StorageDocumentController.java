package dev.wegner.academy_app.storage;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageDocumentController
{

    private final StorageDocumentService storageDocumentService;
    private final StorageDocumentRepository storageDocumentRepository;

    public StorageDocumentController( StorageDocumentService storageDocumentService, StorageDocumentRepository storageDocumentRepository )
    {
        this.storageDocumentService = storageDocumentService;
        this.storageDocumentRepository = storageDocumentRepository;
    }

    @PostMapping(path = "/students/{studentId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StorageDocumentResponse upload( @PathVariable Long studentId, @RequestParam("file") MultipartFile file ) throws Exception
    {
        StorageDocument uploadedDocument = storageDocumentService.upload(studentId, file);
        return new StorageDocumentResponse(uploadedDocument.getId(), uploadedDocument.getFileName(), uploadedDocument.getContentType(), uploadedDocument.getFileSize());
    }

    @GetMapping("/students/{studentId}/documents")
    public List<StorageDocumentResponse> findDocuments( @PathVariable Long studentId )
    {

        return storageDocumentRepository.findByStudentId(studentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/documents/{documentId}/download")
    public ResponseEntity<InputStreamResource> download( @PathVariable Long documentId ) throws Exception
    {
        var document = storageDocumentRepository.findById(documentId)
                .orElseThrow();
        var stream = storageDocumentService.download(documentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(document.getContentType()))
                .body(new InputStreamResource(stream));
    }

    private StorageDocumentResponse toResponse( StorageDocument document )
    {

        return new StorageDocumentResponse(document.getId(), document.getFileName(), document.getContentType(), document.getFileSize());
    }
}
