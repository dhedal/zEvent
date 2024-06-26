package com.ecf.zevent.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Streamer implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String pseudo;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String matricule;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, length = 100)
    private String chaine;
    @Column(nullable = false)
    private Rule rule;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getChaine() {
        return chaine;
    }

    public void setChaine(String chaine) {
        this.chaine = chaine;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Streamer{");
        sb.append("id=").append(id);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", matricule='").append(matricule).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", age=").append(age);
        sb.append(", chaine='").append(chaine).append('\'');
        sb.append(", rule='").append(rule).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(32);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(null == obj || !(obj instanceof Streamer)) return false;
        Streamer that = (Streamer) obj;
        if(this.id == null && that.id == null) {
            return Objects.equals(this.age, that.age) &&
                    Objects.equals(this.chaine, that.chaine) &&
                    Objects.equals(this.createdAt, that.createdAt) &&
                    Objects.equals(this.email, that.email) &&
                    Objects.equals(this.firstName, this.lastName) &&
                    Objects.equals(this.lastName, that.lastName) &&
                    Objects.equals(this.pseudo, that.pseudo) &&
                    Objects.equals(this.matricule, that.matricule) &&
                    Objects.equals(this.rule, that.rule);
        }

        return Objects.equals(this.id, that.id);
    }
}
