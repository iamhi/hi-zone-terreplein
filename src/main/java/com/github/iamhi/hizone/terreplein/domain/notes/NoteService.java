package com.github.iamhi.hizone.terreplein.domain.notes;

import com.github.iamhi.hizone.terreplein.domain.services.UserService;
import com.github.iamhi.hizone.terreplein.in.notes.CreateNoteAction;
import com.github.iamhi.hizone.terreplein.out.notes.NoteEntity;
import com.github.iamhi.hizone.terreplein.out.notes.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class NoteService {

    final NoteRepository noteRepository;

    final UserService userService;

    @Bean
    CreateNoteAction createNoteAction() {
        return noteDto -> userService.getUser().flatMap(userInfoResponse ->
            noteRepository.save(new NoteEntity(
                null,
                UUID.randomUUID().toString(),
                noteDto.title(),
                noteDto.content(),
                userInfoResponse.uuid(),
                Instant.now(),
                Instant.now()
            )).map(NoteDto::fromEntity)
        );
    }
}
