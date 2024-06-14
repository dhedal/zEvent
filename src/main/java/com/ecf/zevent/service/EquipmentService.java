package com.ecf.zevent.service;

import com.ecf.zevent.model.Equipment;
import com.ecf.zevent.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService extends AbstractService<EquipmentRepository, Equipment> {
    @Autowired
    public EquipmentService(EquipmentRepository repository) {
        super(repository);
    }
}
