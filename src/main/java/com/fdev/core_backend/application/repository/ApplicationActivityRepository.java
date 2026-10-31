package com.fdev.core_backend.application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdev.core_backend.application.domain.ApplicationActivity;

public interface ApplicationActivityRepository extends JpaRepository<ApplicationActivity, UUID> {
    List<ApplicationActivity> findAllByApplicationIdOrderByEventDateDesc(UUID applicationId);
}