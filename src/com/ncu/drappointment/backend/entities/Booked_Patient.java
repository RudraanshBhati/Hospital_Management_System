package com.ncu.drappointment.backend.entities;

public class Booked_Patient {

    private String appointment_date;
    private String appointment_time;
    private Integer patient_id;
    private int dr_id;
    private String address;
    private String gender;
    private Integer age;
    private String prescription;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Booked_Patient(String appointment_date, String appointment_time, Integer patient_id, int dr_id, String address, String gender, Integer age , String name) {
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.patient_id = patient_id;
        this.dr_id = dr_id;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.name = name;
    }


    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }


    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public int getDr_id() {
        return dr_id;
    }

    public void setDr_id(int dr_id) {
        this.dr_id = dr_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
