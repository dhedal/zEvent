package com.ecf.zevent.repository;

import com.ecf.zevent.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Long> {


}
