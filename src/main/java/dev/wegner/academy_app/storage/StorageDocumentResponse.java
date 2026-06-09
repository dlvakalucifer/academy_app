package dev.wegner.academy_app.storage;

public record StorageDocumentResponse(Long id, String fileName, String contentType, long fileSize)
{
}