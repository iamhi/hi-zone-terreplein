package com.github.iamhi.hizone.terreplein.domain.models;

public record TruthDto(
    String uuid,
    String content,
    String tags
) {
    public static TruthDto withContent(String content) {
        return new TruthDto(null, content, "");
    }
}
