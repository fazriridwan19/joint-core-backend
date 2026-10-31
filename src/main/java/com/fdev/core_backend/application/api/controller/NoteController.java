package com.fdev.core_backend.application.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdev.core_backend.application.api.dto.NoteDtos;
import com.fdev.core_backend.application.service.NoteService;
import com.fdev.core_backend.identity.domain.UserPrincipal;
import com.fdev.core_backend.shared.api.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class NoteController {
    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostMapping("/applications/{applicationId}/notes")
    ResponseEntity<ApiResponse<NoteDtos.Response>> create(@AuthenticationPrincipal UserPrincipal p,
            @PathVariable UUID applicationId, @Valid @RequestBody NoteDtos.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(service.create(p.getId(), applicationId, request)));
    }

    @GetMapping("/applications/{applicationId}/notes")
    ApiResponse<List<NoteDtos.Response>> list(@AuthenticationPrincipal UserPrincipal p,
            @PathVariable UUID applicationId) {
        return ApiResponse.success(service.list(p.getId(), applicationId));
    }

    @PatchMapping("/notes/{id}")
    ApiResponse<NoteDtos.Response> update(@AuthenticationPrincipal UserPrincipal p, @PathVariable UUID id,
            @Valid @RequestBody NoteDtos.Request request) {
        return ApiResponse.success(service.update(p.getId(), id, request));
    }

    @DeleteMapping("/notes/{id}")
    ApiResponse<Void> delete(@AuthenticationPrincipal UserPrincipal p, @PathVariable UUID id) {
        service.delete(p.getId(), id);
        return ApiResponse.success(null);
    }
}