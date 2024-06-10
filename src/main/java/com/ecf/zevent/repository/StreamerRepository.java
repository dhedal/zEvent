package com.ecf.zevent.repository;

import com.ecf.zevent.model.Streamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamerRepository extends JpaRepository<Streamer, Long> {
}
