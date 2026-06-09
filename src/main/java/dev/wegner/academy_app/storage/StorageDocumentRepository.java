package dev.wegner.academy_app.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageDocumentRepository extends JpaRepository<StorageDocument, Long>
{
    List<StorageDocument> findByStudentId( Long studentId );
}
