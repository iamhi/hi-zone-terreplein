package com.github.iamhi.hizone.terreplein.out.notes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class NoteEntity {

    @Column("id")
    Integer id;

    @Column("uuid")
    String uuid;

    @Column("title")
    String title;

    @Column("content")
    String content;

    @Column("created_by_uuid")
    String createdByUuid;

    @Column("created_at")
    Instant createdAt;

    @Column("updated_at")
    Instant updatedAt;
}
