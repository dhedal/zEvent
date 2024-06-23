package com.ecf.zevent.repository;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.ThematiqueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LiveRepository extends JpaRepository<Live, Long> {

    @Query("SELECT l FROM Live l WHERE FUNCTION('DATE', l.dateStart) = ?1")
    public List<Live> findAllByDate(LocalDate localDate);

    public List<Live> findAllByStreamer(Streamer streamer);

    public List<Live> findAllByTheme(ThematiqueType theme);
}
