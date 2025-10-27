package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository extends JpaRepository<Pack, Long> {
}
