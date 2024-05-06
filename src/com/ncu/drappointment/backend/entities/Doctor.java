package com.ncu.drappointment.backend.entities;

public class Doctor {
    private Integer id;
    private String name;
    private String specialization;
    private String workingHours;

    public Doctor(Integer id, String name, String specialization, String workingHours) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.workingHours = workingHours;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public Integer getId()
    {
        return id;
    }
}
