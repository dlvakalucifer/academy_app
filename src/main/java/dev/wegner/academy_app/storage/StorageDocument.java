package dev.wegner.academy_app.storage;

import dev.wegner.academy_app.student.Student;
import jakarta.persistence.*;

@Entity
@Table(name = StorageDocument.TABLE_NAME)
public class StorageDocument
{

    public static final String TABLE_NAME = "storage_document";

    public static final String ID_COLUMN = "id";
    public static final String OBJECT_KEY_COLUMN = "object_key";
    public static final String FILE_NAME_COLUMN = "file_name";
    public static final String CONTENT_TYPE_COLUMN = "content_type";
    public static final String FILE_SIZE_COLUMN = "file_size";
    public static final String STUDENT_JOIN_COLUMN = "student_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN)
    private Long id;

    @Column(name = OBJECT_KEY_COLUMN)
    private String objectKey;

    @Column(name = FILE_NAME_COLUMN)
    private String fileName;

    @Column(name = CONTENT_TYPE_COLUMN)
    private String contentType;

    @Column(name = FILE_SIZE_COLUMN)
    private long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = STUDENT_JOIN_COLUMN, nullable = false)
    private Student student;

    protected StorageDocument()
    {
        // JPA
    }

    private StorageDocument( String objectKey, String fileName, String contentType, long fileSize, Student student )
    {
        this.objectKey = objectKey;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.student = student;
    }

    public static StorageDocument create( String objectKey, String fileName, String contentType, long fileSize, Student student )
    {
        return new StorageDocument(objectKey, fileName, contentType, fileSize, student);
    }

    public Long getId()
    {
        return id;
    }

    public String getObjectKey()
    {
        return objectKey;
    }

    public String getFileName()
    {
        return fileName;
    }

    public String getContentType()
    {
        return contentType;
    }

    public long getFileSize()
    {
        return fileSize;
    }

    public Student getStudent()
    {
        return student;
    }
}