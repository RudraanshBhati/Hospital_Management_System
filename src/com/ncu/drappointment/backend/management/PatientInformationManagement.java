package com.ncu.drappointment.backend.management;

import com.ncu.drappointment.backend.db.ConnectionDetails;
import com.ncu.drappointment.backend.entities.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientInformationManagement {

    private Connection connection;
    private ArrayList<Patient> patients;
    public PatientInformationManagement()
    {
        this.connection = ConnectionDetails.getConnection();
    }




    public ArrayList<Patient> getPatientsFromDB()
    {
        patients = new ArrayList<>();
        String query = "select * from patients";
        try
        {
            PreparedStatement preparedStatement =  connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n",id, name, age , gender);
                System.out.println("+------------+-------------------+--------+-------------+");
                String address = resultSet.getString("address");
                patients.add(new Patient(id,name,address,gender,age));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return patients;
    }


    public Patient getPatientById(int id)
    {
        Patient patient = null;
        String query = "SELECT * FROM patients where id = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n",id, name, age , gender);
                System.out.println("+------------+-------------------+--------+-------------+");
                String address = resultSet.getString("address");
                patient =  new Patient(id,name,address,gender,age);
            }


        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return patient;
    }

//    public ArrayList<Patient> searchPatientByName(String namepattern)
//    {
//        patients = new ArrayList<>();
//        patients.add(new Patient(1,namepattern,"PVR Gurgaon","M",18));
//        patients.add(new Patient(2,namepattern,"Sector 7 Gurgaon","M",28));
//        return patients;
//    }


    public ArrayList<Patient> searchPatientByNameFromDB(String namepattern)
    {
        patients = new ArrayList<>();
        String query = "SELECT * FROM patients where name like ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , "%".concat(namepattern.concat("%")));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n",id, name, age , gender);
                System.out.println("+------------+-------------------+--------+-------------+");
                String address = resultSet.getString("address");
                patients.add(new Patient(id,name,address,gender,age));
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return patients;
    }

     public void addPatient(String name , int age , String gender , String address)
    {
        try {
            String query = "INSERT INTO patients (name, age, gender, address) VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4 , address);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Patient Added Successfully!!");
            }
            else
            {
                System.out.println("Failed to add Patient!!");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
