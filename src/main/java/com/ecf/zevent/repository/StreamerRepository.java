package com.ecf.zevent.repository;

import com.ecf.zevent.model.Streamer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StreamerRepository extends JpaRepository<Streamer, Long> {

    @Query("SELECT s FROM Streamer s WHERE s.publicData.pseudo = :pseudo")
    public Streamer findByPseudo(String pseudo);

    public Streamer findByUuid(String uuid);
}
