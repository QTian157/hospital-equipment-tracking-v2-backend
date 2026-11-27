package com.qt.hospital_equipment_tracking.repository;

import com.qt.hospital_equipment_tracking.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    // Find a user account by username during login.
    Optional<UserAccount> findByUsername(String username);

    // Check if the username is already taken during registration.
    boolean existsByUsername(String username);

    // Check if the person is already linked to a user account.
    boolean existsByPersonId(Long personId);
}