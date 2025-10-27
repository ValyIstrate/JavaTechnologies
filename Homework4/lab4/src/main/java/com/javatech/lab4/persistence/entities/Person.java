package com.javatech.lab4.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Person {

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false, unique = true)
    protected String email;
}
