package com.javatech.lab4.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "packs")
@AllArgsConstructor
@RequiredArgsConstructor
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "semester", nullable = false)
    private int semester;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL)
    private List<Course> courses;
}
