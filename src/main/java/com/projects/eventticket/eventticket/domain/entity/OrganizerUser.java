package com.projects.eventticket.eventticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="organizer_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerUser {
    @Id
    @Column(name="id",updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="company_name")
    private String companyName;

    @Column(name="display_name")
    private String displayName;

    @Column(name="company_email")
    private String companyEmail;

    @Column(name="company_phone")
    private String companyPhone;

    @Column(name="gst_number")
    private String gstNumber;

    @Column(name="address")
    private String address;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "organizer_user_staff",
            joinColumns = @JoinColumn(name = "organizer_user_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<User> staffs = new ArrayList<>();

    @CreatedDate
    @Column(name="created_at",updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;
}
