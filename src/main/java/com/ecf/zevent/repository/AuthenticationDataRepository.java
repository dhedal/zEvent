package com.ecf.zevent.repository;

import com.ecf.zevent.model.AuthenticationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationDataRepository extends JpaRepository<AuthenticationData, Long> {

    public Optional<AuthenticationData> findByEmail(String email);
}
