package com.ecf.zevent.repository;

import com.ecf.zevent.controller.LiveController;
import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.ThematiqueType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Parameter;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

public class LiveRepositoryCustomImpl implements LiveRepositoryCustom {
    private static final Logger LOG = LoggerFactory.getLogger(LiveRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     * @param date
     * @param thematiqueType
     * @param streamer
     * @return
     */
    @Override
    public List<Live> findLivesBy(LocalDate date, ThematiqueType thematiqueType, Streamer streamer) {
        List<String> conditions = new ArrayList<>();

        if(date != null) {
            conditions.add("FUNCTION('DATE', l.dateStart) = :date");
        }

        if(streamer != null) {
            conditions.add("l.streamer = :streamer");
        }

        if(thematiqueType != null && !Objects.equals(ThematiqueType.NONE, thematiqueType)) {
            conditions.add("l.themes LIKE concat('%', :theme, '%')");
        }

        StringBuilder rq = new StringBuilder("SELECT l FROM Live l WHERE ");
        if(!conditions.isEmpty()) {
            rq.append(String.join(" AND ", conditions));
        }

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        Query query = this.entityManager.createQuery(rq.toString());
        if(date != null) query.setParameter("date", date);
        if(streamer != null) query.setParameter("streamer", streamer);
        if(thematiqueType != null) query.setParameter("theme", thematiqueType.getKey().toString());

        LOG.info("***********************************************");
        LOG.info(rq.toString());
        return query.getResultList();
    }

    /**
     *
     * @param thematiqueType
     * @return
     */
    public List<Live> findLivesByTheme(ThematiqueType thematiqueType){
        if(thematiqueType == null || Objects.equals(ThematiqueType.NONE, thematiqueType)) {
            return List.of();
        }
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        StringBuilder rq = new StringBuilder("SELECT l FROM Live l WHERE l.themes LIKE concat('%', :theme, '%')");
        Query query = this.entityManager.createQuery(rq.toString());
        query.setParameter("theme", thematiqueType.getKey().toString());

        LOG.info("***********************************************");
        LOG.info(rq.toString());
        return query.getResultList();

    }

    /**
     *
     * @param thematiqueTypes
     * @return
     */
    @Override
    public List<Live> findLivesByThemes(List<ThematiqueType> thematiqueTypes) {
        if(thematiqueTypes == null || thematiqueTypes.isEmpty()) return List.of();

        StringBuilder rq = new StringBuilder("SELECT DISTINCT l FROM Live l WHERE ");

        List<String> conditions = new ArrayList<>();
        Map<String, ThematiqueType> pMap = new HashMap<>();
        String pName = "theme";
        for(int i = 0; i < thematiqueTypes.size(); i++){
            ThematiqueType thematiqueType = thematiqueTypes.get(i);
            if(Objects.nonNull(thematiqueType) && !Objects.equals(ThematiqueType.NONE, thematiqueType)) {
                String p = pName + i;
                conditions.add("l.themes LIKE concat('%', :"+ p +", '%')");
                pMap.put(p, thematiqueType);
            }
        }
        if(conditions.isEmpty()) return List.of();
        rq.append(String.join(" OR ", conditions));
        LOG.info("***********************************************");
        LOG.info(rq.toString());

        Query query = this.entityManager.createQuery(rq.toString());
        LOG.info(pMap.toString());
        for(Map.Entry<String, ThematiqueType> entry: pMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue().getKey().toString());
        }

        return query.getResultList();
    }
}
