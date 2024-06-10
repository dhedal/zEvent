package com.ecf.zevent.service;

import com.ecf.zevent.model.Materiel;
import com.ecf.zevent.repository.MaterielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterielService extends AbstractService<MaterielRepository, Materiel> {
    @Autowired
    public MaterielService(MaterielRepository repository) {
        super(repository);
    }
}
