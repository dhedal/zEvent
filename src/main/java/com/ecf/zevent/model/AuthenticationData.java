package com.ecf.zevent.model;

import com.ecf.zevent.model.interfaces.IEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class AuthenticationData implements IEntity, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 255)
    private String password;

    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthenticationData{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(35);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        AuthenticationData that = (AuthenticationData) obj;
        if(null == obj || !(obj instanceof AuthenticationData)) return false;
        if(this.id == null && that.id == null){
            return Objects.equals(this.email, that.email) &&
                    Objects.equals(this.password, that.password);
        }
        return Objects.equals(this.id, that.id);
    }


    public Long getId() {
        return id;
    }
    @Override
    public void setUuid(String uuid) {}
    @Override
    public void setCreatedAt(LocalDateTime createdAt) {}
    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return true;
    }
}
