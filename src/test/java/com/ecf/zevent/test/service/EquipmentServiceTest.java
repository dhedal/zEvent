package com.ecf.zevent.test.service;

import com.ecf.zevent.model.Equipment;
import com.ecf.zevent.model.EquipmentType;
import com.ecf.zevent.service.EquipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EquipmentServiceTest {
    @Autowired
    private EquipmentService equipmentService;

    private Equipment newEquipment(EquipmentType equipmentType, String label, String brand, int quantity) {
        Equipment equipment = new Equipment();
        equipment.setEquipmentType(equipmentType);
        equipment.setLabel(label);
        equipment.setBrand(brand);
        equipment.setQuantity(quantity);

        return equipment;
    }

    @Test
    public void testCreateAndSaveNewEquipment() {
        String brand = "Samsung";
        Integer quantity = 2;
        Equipment equipment = this.newEquipment(EquipmentType.CAMERA, EquipmentType.CAMERA.getLabel(), brand, quantity);
        equipment = this.equipmentService.save(equipment);
        assertNotNull(equipment.getId());

        try {
            Equipment equipmentSaved = this.equipmentService.findById(equipment.getId());
            assertNotNull(equipmentSaved);
            assertNotNull(equipmentSaved.getCreatedAt());
            assertNull(equipmentSaved.getUpdatedAt());
            assertEquals(EquipmentType.CAMERA, equipmentSaved.getEquipmentType());
            assertEquals(brand, equipmentSaved.getBrand());
            assertEquals(quantity, equipmentSaved.getQuantity());
        } catch(Throwable ex){
            fail(ex.toString());
        }

    }

    @Test
    public void testEquipmentUpdated() {
        String brand = "Samsung";
        Integer quantity = 2;
        Equipment equipment = this.newEquipment(EquipmentType.CAMERA, EquipmentType.CAMERA.getLabel(), brand, quantity);
        equipment = this.equipmentService.save(equipment);
        assertNotNull(equipment.getId());

        try {
            Equipment equipmentSaved = this.equipmentService.findById(equipment.getId());
            assertNotNull(equipmentSaved);
            String newBrand = "Sony";
            equipmentSaved.setBrand(newBrand);
            this.equipmentService.save(equipmentSaved);

            Equipment equipmentUpdated = this.equipmentService.findById(equipment.getId());
            assertNotNull(equipmentUpdated.getCreatedAt());
            assertNotNull(equipmentUpdated.getUpdatedAt());
            assertEquals(newBrand, equipmentUpdated.getBrand());
        } catch(Throwable ex){
            fail(ex.toString());
        }
    }

    @Test
    public void testDeleteEquipment(){
        String brand = "Samsung";
        Integer quantity = 2;
        final Equipment equipment = this.equipmentService.save(
                this.newEquipment(EquipmentType.CAMERA, EquipmentType.CAMERA.getLabel(), brand, quantity));
        assertNotNull(equipment.getId());

        assertThrows(ResourceNotFoundException.class, () -> {
            this.equipmentService.delete(equipment.getId());
            this.equipmentService.findById(equipment.getId());
        });
    }

    @Test public void testEquipmentAll(){
        int equipmentCount = this.equipmentService.listAll().size();

        List<Equipment> equipments = List.of(
            this.newEquipment(EquipmentType.CAMERA, EquipmentType.CAMERA.getLabel(), "Sony", 4),
            this.newEquipment(EquipmentType.COMPUTER, EquipmentType.COMPUTER.getLabel(), "Asus", 4),
            this.newEquipment(EquipmentType.HEADPHONES, EquipmentType.HEADPHONES.getLabel(), "Logitech", 4)
        );

        equipments.forEach(equipment -> this.equipmentService.save(equipment));

        assertEquals(equipmentCount + equipments.size(), this.equipmentService.listAll().size());
    }

}
