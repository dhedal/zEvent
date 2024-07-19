package com.ecf.zevent.repository;

import com.ecf.zevent.model.AuthenticationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationDataRepository extends JpaRepository<AuthenticationData, Long> {

    public AuthenticationData findByEmail(String email);
}
