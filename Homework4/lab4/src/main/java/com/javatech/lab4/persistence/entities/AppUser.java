package com.javatech.lab4.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "app_user")
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 200)
    private String userEmail;

    @Column(name = "role")
    private String role;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "is_locked")
    private Boolean isLocked = false;

    @Column(name = "is_activated")
    private Boolean isActivated = false;

    @Column(name = "password")
    private String password;

    public AppUser(Student newStudent) {
        this.userEmail = newStudent.getEmail();
        this.role = "student";
        this.entityId = newStudent.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toUpperCase()));
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActivated;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
