package com.fdev.core_backend.application.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdev.core_backend.application.api.dto.TimelineDtos;
import com.fdev.core_backend.application.service.TimelineService;
import com.fdev.core_backend.identity.domain.UserPrincipal;
import com.fdev.core_backend.shared.api.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/applications/{applicationId}/timeline")
public class TimelineController {
    private final TimelineService service;

    public TimelineController(TimelineService service) {
        this.service = service;
    }

    @GetMapping
    ApiResponse<List<TimelineDtos.Response>> list(@AuthenticationPrincipal UserPrincipal p,
            @PathVariable UUID applicationId) {
        return ApiResponse.success(service.list(p.getId(), applicationId));
    }

    @PostMapping
    ApiResponse<TimelineDtos.Response> create(@AuthenticationPrincipal UserPrincipal p,
            @PathVariable UUID applicationId, @Valid @RequestBody TimelineDtos.CreateRequest request) {
        return ApiResponse.success(service.create(p.getId(), applicationId, request));
    }
}