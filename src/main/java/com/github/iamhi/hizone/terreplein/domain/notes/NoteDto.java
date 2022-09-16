package com.github.iamhi.hizone.terreplein.domain.notes;

import com.github.iamhi.hizone.terreplein.out.notes.NoteEntity;

import java.time.Instant;

public record NoteDto(
    String uuid,
    String title,
    String content,
    String createdByUuid,
    Instant createdAt,
    Instant updatedAt
) {
    public static NoteDto withTitleAndContent(String title, String content) {
        return new NoteDto(
            null,
            title,
            content,
            null,
            null,
            null
        );
    }

    static NoteDto fromEntity(NoteEntity entity) {
        return new NoteDto(
            entity.getUuid(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreatedByUuid(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    NoteEntity asEntity(Integer id) {
        return new NoteEntity(
            id,
            uuid,
            title,
            content,
            createdByUuid,
            createdAt,
            updatedAt
        );
    }

    NoteEntity updateEntity(NoteEntity noteEntity) {
        return new NoteEntity(
            noteEntity.getId(),
            noteEntity.getUuid(),
            title != null ? title : noteEntity.getTitle(),
            content != null ? content : noteEntity.getContent(),
            noteEntity.getCreatedByUuid(),
            noteEntity.getCreatedAt(),
            Instant.now()
        );
    }
}
