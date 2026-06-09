package dev.wegner.academy_app.storage;

import dev.wegner.academy_app.student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StorageDocumentController.class)
class StorageDocumentControllerTest
{
    @MockitoBean
    StorageDocumentService service;

    @MockitoBean
    StorageDocumentRepository repository;

    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldUploadFile() throws Exception
    {
        when(service.upload(any(), any())).thenReturn(StorageDocument.create("", "", "", 1L, Student.create("", "", "")));

        mvc.perform(multipart("http://localhost:8081/storage/students/1/documents").file(new MockMultipartFile("file", "test.txt", "text/plain", "hello".getBytes())))
                .andExpect(status().isOk());
    }
}
