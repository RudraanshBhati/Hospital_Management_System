package com.ncu.drappointment.backend.management;

import com.ncu.drappointment.backend.db.ConnectionDetails;
import com.ncu.drappointment.backend.entities.Doctor;
import com.ncu.drappointment.backend.entities.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DoctorInformationManagement {
    private Connection connection;
    private ArrayList<Doctor> doctors;
    public DoctorInformationManagement()
    {
        this.connection = ConnectionDetails.getConnection();
    }


//    public ArrayList<Doctor> getDoctors(){
//        doctors = new ArrayList<>();
//        doctors.add(new Doctor(1,"Dr. Smith", "General Practitioner", "9:00 AM - 5:00 PM"));
//        doctors.add(new Doctor(2,"Dr. Johnson", "Pediatrician", "10:00 AM - 6:00 PM"));
//        return doctors;
//    }

    public ArrayList<Doctor> getDoctorsFromDB()
    {
        doctors = new ArrayList<>();


        String query = "select * from doctors";
        try
        {
            PreparedStatement preparedStatement =  connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+-------------------+------------------+");
            System.out.println("| Doctor Id | Name              | Specialization   |Working Hours         |");
            System.out.println("+------------+-------------------+--------+---------+");
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                String workingHours = resultSet.getString("workinghours");
                System.out.printf("|%-12s|%-20s|%-18s|%-20s|\n", id, name , specialization,workingHours);
                System.out.println("+------------+-------------------+--------+---------+");
                doctors.add(new Doctor(id,name, specialization, workingHours));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return doctors;
    }
    public boolean getDoctorById(int id)
    {
        String query = "SELECT * FROM doctors where id = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<Doctor> getDoctorsFromDB(String _specialization)
    {
        doctors = new ArrayList<>();


        String query = "select * from doctors WHERE specialization = ?";
        try
        {
            PreparedStatement preparedStatement =  connection.prepareStatement(query);
            preparedStatement.setString(1, _specialization);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+-------------------+------------------+");
            System.out.println("| Doctor Id | Name              | Specialization   |Working Hours         |");
            System.out.println("+------------+-------------------+--------+---------+");
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                String workingHours = resultSet.getString("workinghours");
                System.out.printf("|%-12s|%-20s|%-18s|%-20s|\n", id, name , specialization,workingHours);
                System.out.println("+------------+-------------------+--------+---------+");
                doctors.add(new Doctor(id,name, specialization, workingHours));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return doctors;
    }

public void addDoctor(String name , String specialization , String workingHours)
{
    String query = "INSERT INTO DOCTORS (name , specialization , workingHours) VALUES(?, ?, ?)";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2,specialization);
        preparedStatement.setString(3,workingHours);
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows > 0)
        {
            System.out.println("Doctor Added Successfully!!");
        }
        else
        {
            System.out.println("Failed to add Doctor!!");
        }
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

}




}
