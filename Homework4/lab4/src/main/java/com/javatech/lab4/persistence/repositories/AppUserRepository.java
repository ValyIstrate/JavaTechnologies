package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserEmail(String userEmail);
}
