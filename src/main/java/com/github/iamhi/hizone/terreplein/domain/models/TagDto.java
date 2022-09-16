package com.github.iamhi.hizone.terreplein.domain.models;

public record TagDto(
    String uuid,
    String name,
    String references
) {
    public TagDto(String uuid, String name) {
        this(uuid, name, "");
    }

    public TagDto(String name) {
        this("", name, "");
    }
}
