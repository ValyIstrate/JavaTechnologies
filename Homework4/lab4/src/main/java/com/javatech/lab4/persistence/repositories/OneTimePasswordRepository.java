package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {
    Optional<OneTimePassword> findByUserEmail(String userEmail);
}
