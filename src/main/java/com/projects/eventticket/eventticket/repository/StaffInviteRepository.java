package com.projects.eventticket.eventticket.repository;

import com.projects.eventticket.eventticket.domain.entity.StaffInvites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StaffInviteRepository extends JpaRepository<StaffInvites, UUID> {

}
