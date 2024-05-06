//package com.ncu.drappointment.backend;
//
//import javax.print.Doc;
//import java.sql.*;
//        import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.Scanner;
//
//public class HospitalManagementSystem {
//    private static final String url = "jdbc:mysql://localhost:3306/hospital";
//    private static final String username = "root";
//    private static final String password = "Rudraansh0405";
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        try
//        {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        }
//        catch (ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        try
//        {
//            Connection connection = DriverManager.getConnection(url, username, password);
//            Patient patient = new Patient(connection,scanner);
//            Doctor doctor = new Doctor(connection);
//
//            boolean var = true;
//            while (var)
//            {
//                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
//                System.out.println("1. Add Patients");
//                System.out.println("2. View Patients");
//                System.out.println("3. View Doctors");
//                System.out.println("4. Book Appointment");
//                System.out.println("5. Exit");
//                System.out.println("Enter your choice: ");
//                int choice = scanner.nextInt();
//
//                switch (choice)
//                {
//                    case 1:
//                        patient.addPatient();
//                        System.out.println();
//                        break;
//                    case 2:
//                        patient.viewPatients();
//                        System.out.println();
//                        break;
//                    case 3:
//                        doctor.viewDoctors();
//                        System.out.println();
//                        break;
//                    case 4:
//                        bookAppointment(patient, doctor, connection ,scanner);
//                        break;
//                    case 5:
//                        var = false;
//                        System.out.println("Thanks for using this system");
//                        break;
//                    default:
//                        System.out.println("Enter a valid choice!!");
//                }
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public static void bookAppointment(Patient patient , Doctor doctor , Connection connection, Scanner scanner) {
//        System.out.println("Enter Patient Id: ");
//        int patiendId = scanner.nextInt();
//        System.out.println("Enter Doctor Id: ");
//        int doctorId = scanner.nextInt();
//        System.out.println("Enter Appointment Date (YYYY-MM-DD):");
//        String appointmentDate = scanner.next();
//        System.out.println("Enter the Appointment Time (hh:mm:ss):");
//        String startTime = scanner.next();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        LocalTime localTime = LocalTime.parse(startTime, formatter);
//        LocalTime endTime =  localTime.plus(30, ChronoUnit.MINUTES);
//
//
//        Time time = Time.valueOf(localTime);
//        if (patient.getPatientById(patiendId) && doctor.getDoctorById(doctorId)) {
//            if (checkDoctorAvailability(doctorId, appointmentDate, startTime, connection)) {
//                String appointmentQuery = "INSERT INTO appointments(patient_id , doctor_id, appointment_date, startTime , endTime) VALUES(?, ?, ?, ?, ?)";
//                try {
//                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
//                    preparedStatement.setInt(1, patiendId);
//                    preparedStatement.setInt(2, doctorId);
//                    preparedStatement.setString(3, appointmentDate);
//                    preparedStatement.setString(4, startTime);
//                    preparedStatement.setString(5,endTime.toString() );
//                    int rowsAffected = preparedStatement.executeUpdate();
//                    if (rowsAffected > 0) {
//                        System.out.println("Appointment Booked!!");
//                        System.out.println();
//                    } else {
//                        System.out.println("Failed to Book Appointment!");
//                        System.out.println();
//                    }
//                } catch (SQLException e) {
//
//                    if (e.getMessage().contains("mustStartOnTenMinuteBoundary"))
//                    {
//                        System.out.println("Appointment not booked!! Appointments are available only every 30 minutes starting from the hour (:00) or the half-hour (:30).\n");
//                    }
//                    else
//                    {
//                        e.printStackTrace();
//                    }
//
//                }
//            } else {
//                System.out.println("Doctor not available on this date!!");
//                System.out.println();
//            }
//        } else {
//            System.out.println("Either doctor or patient does not exist!!");
//            System.out.println();
//        }
//    }
//
//    public static boolean checkDoctorAvailability(int doctorId , String appointmentDate , String startTime ,  Connection connection)
//    {
//        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ? AND (startTime <= ? AND endTime >= ?)";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1 , doctorId);
//            preparedStatement.setString(2, appointmentDate);
//            preparedStatement.setString(3,startTime);
//            preparedStatement.setString(4 , startTime);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if(resultSet.next())
//            {
//                int count = resultSet.getInt(1);
//                if (count == 0)
//                {
//                    return true;
//                }
//                else
//                {
//                    return false;
//                }
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//}
