package com.ecf.zevent.repository;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.ThematiqueType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LiveRepositoryCustomImpl implements LiveRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Live> findLivesBy(LocalDate date, ThematiqueType thematiqueType, Streamer streamer) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        StringBuilder rq = new StringBuilder("SELECT l FROM Live l WHERE ");
        List<String> conditions = new ArrayList<>();

        if(date != null) {
            conditions.add("FUNCTION('DATE', l.dateStart) = :date");
        }

        if(streamer != null) {
            conditions.add("l.streamer = :streamer");
        }

        if(thematiqueType != null) {
            conditions.add("theme = :theme");
        }

        if(!conditions.isEmpty()) {
            rq.append(String.join(" AND ", conditions));
        }

        Query query = this.entityManager.createQuery(rq.toString());
        if(date != null) query.setParameter("date", date);
        if(streamer != null) query.setParameter("streamer", streamer);
        if(thematiqueType != null) query.setParameter("theme", thematiqueType);

        System.out.println("***********************************************");
        System.out.println(rq);
        return query.getResultList();
    }
}
