package com.ncu.drappointment.backend.management;

import com.ncu.drappointment.backend.db.ConnectionDetails;
import com.ncu.drappointment.backend.entities.Booked_Patient;
import com.ncu.drappointment.backend.entities.Patient;
import com.ncu.drappointment.backend.entities.Slot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SlotsManagement {
    private ArrayList<Slot> slots;
    private Connection connection;

    public SlotsManagement() {
        this.connection = ConnectionDetails.getConnection();
    }

    public ArrayList<Slot> getSlots(int doctor_id) {
        slots = new ArrayList<>();
        String query = "SELECT * FROM Appointment_Slots WHERE doctor_id = ? AND ((appointment_date > CURDATE()) OR (appointment_date = CURDATE() AND startTime >= CURTIME())) AND patient_id IS NULL ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , doctor_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String appointment_date = resultSet.getString("appointment_date");
                int  _doctor_id = resultSet.getInt("doctor_id");
                String startTime = resultSet.getString("startTime");
                int patient_id = resultSet.getInt("patient_id");
                slots.add(new Slot(appointment_date,startTime, patient_id ,_doctor_id  ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }
    public void updateSlot(int doctor_id , int patient_id , String appointment_date , String startTime)
    {
        String query = "UPDATE APPOINTMENT_SLOTS SET patient_id = ?  WHERE  doctor_id = ? AND appointment_date = ? AND startTime = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,patient_id);
            preparedStatement.setInt(2,doctor_id);
            preparedStatement.setString(3,appointment_date);
            preparedStatement.setString(4,startTime);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Slot booked successfully!");
            } else {
                System.out.println("Slot booking failed.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public ArrayList<Booked_Patient> getSlotsForDoctor(int doctor_id)
    {
        ArrayList<Booked_Patient> bookedPatients = new ArrayList<>();

        String query = "SELECT * FROM Appointment_Slots s , patients p WHERE s.patient_id = p.id AND doctor_id = ? AND appointment_date = CURDATE() AND status IS NULL ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , doctor_id);
//            preparedStatement.setInt(2, patient_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String startTime = resultSet.getString("startTime");
                String name = resultSet.getString("name");
                int patient_id = resultSet.getInt("patient_id");
                String address = resultSet.getString("address");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String appointment_date = resultSet.getString("appointment_date");

                bookedPatients.add(new Booked_Patient(appointment_date,startTime, patient_id , doctor_id , address , gender , age , name));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookedPatients ;
    }

    public void updateStatus(Booked_Patient bookedPatient)
    {
        String query = "UPDATE APPOINTMENT_SLOTS SET STATUS = 'DONE' , PRESCRIPTION = ? WHERE doctor_id = ? AND patient_id = ? AND appointment_date = ? AND startTime = ? ";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , bookedPatient.getPrescription());
            preparedStatement.setInt(2, bookedPatient.getDr_id());
            preparedStatement.setInt(3, bookedPatient.getPatient_id());
            preparedStatement.setString(4,bookedPatient.getAppointment_date());
            preparedStatement.setString(5,bookedPatient.getAppointment_time());
            preparedStatement.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

}




