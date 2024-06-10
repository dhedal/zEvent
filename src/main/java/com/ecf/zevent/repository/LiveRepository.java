package com.ecf.zevent.repository;

import com.ecf.zevent.model.Live;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveRepository extends JpaRepository<Live, Long> {
}
