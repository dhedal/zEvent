package com.ecf.zevent.model;

import com.ecf.zevent.model.interfaces.IEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AuthenticationData implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 30)
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
}
