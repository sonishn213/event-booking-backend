package com.projects.eventticket.eventticket.repository;

import com.projects.eventticket.eventticket.domain.entity.OrganizerUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizerUserRepository extends JpaRepository<OrganizerUser, UUID> {
    Optional<OrganizerUser> findByUserId(UUID id);

    boolean existsByUserIdAndStaffsEmail(UUID id,String email);

    Page<OrganizerUser> findByStaffs_Id(UUID staffId, Pageable pageable);

}
