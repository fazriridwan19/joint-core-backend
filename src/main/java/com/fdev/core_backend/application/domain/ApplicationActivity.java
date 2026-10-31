package com.fdev.core_backend.application.domain;

import java.time.Instant;
import java.util.UUID;

import com.fdev.core_backend.application.domain.ApplicationEnums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trx_application_activities", schema = "j_application")
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
public class ApplicationActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "job_application_id", nullable = false)
    private UUID applicationId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "actor_type", nullable = false)
    private String actorType;

    /**
     * Populated only when eventType = STATUS_CHANGED.
     * Nullable — other event types leave this blank.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", length = 30)
    private Status previousStatus;

    /**
     * Populated only when eventType = STATUS_CHANGED.
     * Nullable — other event types leave this blank.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", length = 30)
    private Status newStatus;

    @Column(name = "event_date", nullable = false)
    private Instant eventDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    /** General-purpose constructor — for manual entries and non-status events. */
    public ApplicationActivity(UUID applicationId, String eventType, String description, String actorType) {
        this.applicationId = applicationId;
        this.eventType = eventType;
        this.description = description;
        this.actorType = actorType;
        this.eventDate = Instant.now();
    }

    /** Status-change constructor — carries structured audit data alongside human-readable description. */
    public ApplicationActivity(UUID applicationId, String eventType, String description, String actorType,
            Status previousStatus, Status newStatus) {
        this(applicationId, eventType, description, actorType);
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
    }
}
