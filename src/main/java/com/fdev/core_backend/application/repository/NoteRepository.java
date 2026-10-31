package com.fdev.core_backend.application.repository;

import com.fdev.core_backend.application.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findAllByApplicationIdAndDeletedAtIsNullOrderByPinnedDescCreatedAtDesc(UUID applicationId);

    Optional<Note> findByIdAndApplicationIdAndDeletedAtIsNull(UUID id, UUID applicationId);
}