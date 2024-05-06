package com.ncu.drappointment.backend.entities;

public class Slot {

    public Slot(String appointment_date, String appointment_time, Integer patient_id, int dr_id) {
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.patient_id = patient_id;
        this.dr_id = dr_id;
    }

    private String appointment_date;
    private String appointment_time;
    private Integer patient_id;
    private int dr_id;

    public String getDate() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getTime() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public int getPatientID() {
        return patient_id;
    }

    public void setPatientID(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDr_id() {
        return dr_id;
    }

    public boolean isBooked() {
        return patient_id == null ? false:true;
    }

    public void setDr_id(int dr_id) {
        this.dr_id = dr_id;
    }


}
