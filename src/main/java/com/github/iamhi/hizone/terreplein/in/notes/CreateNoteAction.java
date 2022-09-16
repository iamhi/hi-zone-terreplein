package com.github.iamhi.hizone.terreplein.in.notes;

import com.github.iamhi.hizone.terreplein.domain.notes.NoteDto;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface CreateNoteAction extends Function<NoteDto, Mono<NoteDto>> {
}
