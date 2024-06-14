package com.ecf.zevent.model;

public enum EquipmentType {

    COMPUTER("Ordinateur"),
    MICROPHONE("Microphone"),
    HEADPHONES("Casque audio"),
    CAMERA("Camera"),
    LIGHTING("Eclairage"),
    CAPTURE_CARD("Carte de capture"),
    MONITOR("Moniteur"),
    ACCESORIES("Accessoires"),
    SOFTWARE("Logiciel"),
    FURNITURE("Furniture"),
    INTERNET_CONNECTION("Connection internet");

    private String label;

    EquipmentType(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
