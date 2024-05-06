package com.ncu.drappointment.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DoctorAppointmentLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;



    public DoctorAppointmentLogin() {
        super("Login");

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some spacing

        JLabel usernameLabel = new JLabel("Username:");
        mainPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JPasswordField passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span the button across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simulate login logic
                if (username.equals("receptionist") && password.equals("123")) {
                    openDoctorAppointmentApp("Receptionist");
                } else if (username.equals("admin") && password.equals("admin")) {
                    openDoctorAppointmentApp("Administrator");
                } else if (username.equals("doctor1") && password.equals("doctor1")) {
                    openDoctorAppointmentApp("Doctor" , 1);
                }
                else if (username.equals("doctor2") && password.equals("doctor2")) {
                    openDoctorAppointmentApp("Doctor", 2);
                }
                else if (username.equals("doctor3") && password.equals("doctor3")) {
                    openDoctorAppointmentApp("Doctor", 3);
                }
                else if (username.equals("doctor4") && password.equals("doctor4")) {
                    openDoctorAppointmentApp("Doctor", 4);
                }
                else if (username.equals("doctor5") && password.equals("doctor5")) {
                    openDoctorAppointmentApp("Doctor", 5);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void openDoctorAppointmentApp(String role) {
        SwingUtilities.invokeLater(() -> {
            if (role.equals("Receptionist")) {
                new DoctorAppointmentApp("Receptionist");
            } else if (role.equals("Administrator")) {
                new DoctorAppointmentApp("Administrator");
            }
        });
        dispose(); // Close login window
    }

    private void openDoctorAppointmentApp(String role , int doctor_id) {
        SwingUtilities.invokeLater(() -> {
                if (role.equals("Doctor")) {
                new DoctorAppointmentApp("Doctor" , doctor_id);
            }
        });
        dispose(); // Close login window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoctorAppointmentLogin());
    }
}