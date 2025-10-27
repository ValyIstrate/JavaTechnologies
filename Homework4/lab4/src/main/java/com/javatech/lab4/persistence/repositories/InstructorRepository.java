package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
