package com.projects.eventticket.eventticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="staff_invites")
public class StaffInvites  {
    @Id
    @Column(name="id",updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="staff_email",nullable=false,updatable = false)
    private String staffEmail;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="organizer_user_id",nullable = false)
    private OrganizerUser organizerUser;

    @CreatedDate
    @Column(name="created_at",updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;



}
