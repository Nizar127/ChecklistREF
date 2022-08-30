package com.CEFS.checklist.Model;

public class FomData {
    String ID;
    String foam_tender;
    String registration_num;
    String date;
    String foam_capacity;
    String water_capacity;
    String shift_type;
    String shift1;
    String shift2;
    String equipment_list;
    String qty;
    String cabinet_type;

    public FomData(String ID, String foam_tender, String registration_num, String date, String foam_capacity, String water_capacity, String shift_type, String shift1, String shift2, String equipment_list, String qty, String cabinet_type) {
        this.ID = ID;
        this.foam_tender = foam_tender;
        this.registration_num = registration_num;
        this.date = date;
        this.foam_capacity = foam_capacity;
        this.water_capacity = water_capacity;
        this.shift_type = shift_type;
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.equipment_list = equipment_list;
        this.qty = qty;
        this.cabinet_type = cabinet_type;
    }

    public FomData() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFoam_tender() {
        return foam_tender;
    }

    public void setFoam_tender(String foam_tender) {
        this.foam_tender = foam_tender;
    }

    public String getRegistration_num() {
        return registration_num;
    }

    public void setRegistration_num(String registration_num) {
        this.registration_num = registration_num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoam_capacity() {
        return foam_capacity;
    }

    public void setFoam_capacity(String foam_capacity) {
        this.foam_capacity = foam_capacity;
    }

    public String getWater_capacity() {
        return water_capacity;
    }

    public void setWater_capacity(String water_capacity) {
        this.water_capacity = water_capacity;
    }

    public String getShift_type() {
        return shift_type;
    }

    public void setShift_type(String shift_type) {
        this.shift_type = shift_type;
    }

    public String getShift1() {
        return shift1;
    }

    public void setShift1(String shift1) {
        this.shift1 = shift1;
    }

    public String getShift2() {
        return shift2;
    }

    public void setShift2(String shift2) {
        this.shift2 = shift2;
    }

    public String getEquipment_list() {
        return equipment_list;
    }

    public void setEquipment_list(String equipment_list) {
        this.equipment_list = equipment_list;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCabinet_type() {
        return cabinet_type;
    }

    public void setCabinet_type(String cabinet_type) {
        this.cabinet_type = cabinet_type;
    }
}
