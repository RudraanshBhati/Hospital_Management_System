package com.ncu.drappointment.frontend;

import com.ncu.drappointment.backend.entities.Booked_Patient;
import com.ncu.drappointment.backend.entities.Doctor;
import com.ncu.drappointment.backend.entities.Patient;
import com.ncu.drappointment.backend.entities.Slot;
import com.ncu.drappointment.backend.management.DoctorInformationManagement;
import com.ncu.drappointment.backend.management.PatientInformationManagement;
import com.ncu.drappointment.backend.management.SlotsManagement;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DoctorAppointmentApp extends JFrame {
    private JPanel mainPanel;
    private JPanel patientListPanel;
    private JPanel doctorListPanel;
    private JPanel slotListPanel;
    private JTextField patientSearchField;
    private JTextField doctorSearchField;
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;
    private int logged_doctor_id;
    private Booked_Patient selectedPatient;

    public DoctorAppointmentApp(String role , int doctor_id)
    {
        super("Doctor Appointment Booking");
        doctors = new ArrayList<>();
        patients = new ArrayList<>();

        logged_doctor_id = doctor_id;

        ImageIcon backgroundImage = new ImageIcon(resizeImage("src/Booking.jpg", 800, 600));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        backgroundLabel.add(mainPanel);

        setPreferredSize(new Dimension(800, 600));
        mainPanel.add(new JLabel("Welcome to Doctor Appointment Booking System"));
        patientListPanel = new JPanel();
        patientListPanel.setLayout(new BoxLayout(patientListPanel, BoxLayout.Y_AXIS));
        mainPanel.add(patientListPanel);
        doctorListPanel = new JPanel();
        doctorListPanel.setLayout(new BoxLayout(doctorListPanel, BoxLayout.Y_AXIS));
        mainPanel.add(doctorListPanel);
        slotListPanel = new JPanel();
        slotListPanel.setLayout(new BoxLayout(slotListPanel, BoxLayout.Y_AXIS));
        mainPanel.add(slotListPanel);

        setContentPane(backgroundLabel); // Set background label as the content pane
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar(role));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public DoctorAppointmentApp(String role) {
        this(role, -1);                           // -1 means either logged in with admin or receptionist
    }


    private Image resizeImage(String imagePath, int width, int height) {
        Image img = new ImageIcon(imagePath).getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return resizedImage;
    }

    private JMenuBar createMenuBar(String role) {
        JMenuBar menuBar = new JMenuBar();

        if (role.equals("Administrator")) {
            JMenu administratorMenu = new JMenu("Administrator");
            JMenuItem addDoctorItem = new JMenuItem("Add/Update Doctor Details");
            addDoctorItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showDoctorDetailsForm();
                }
            });
            administratorMenu.add(addDoctorItem);
            menuBar.add(administratorMenu);
        } else if (role.equals("Receptionist")) {
            JMenu receptionistMenu = new JMenu("Receptionist");

            JMenuItem addPatientItem = new JMenuItem("Add/Update Patient Details");
            addPatientItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showPatientDetailsForm();
                }
            });
            receptionistMenu.add(addPatientItem);

            JMenuItem reserveAppointmentItem = new JMenuItem("Reserve/Update Appointment Slots");
            reserveAppointmentItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showReservationPanel();
                }
            });
            receptionistMenu.add(reserveAppointmentItem);

            menuBar.add(receptionistMenu);
        } else if (role.equals("Doctor")) {
            JMenu doctorMenu = new JMenu("Doctor");

//            doctorMenu.add(fillSummaryItem);
            JMenuItem markAppointmentDoneItem = new JMenuItem("Update Appointment Status");
            markAppointmentDoneItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateAppointmentStatus();

                }
            });
            doctorMenu.add(markAppointmentDoneItem);
            menuBar.add(doctorMenu);
        }

        return menuBar;
    }

    private void showReservationPanel() {
        patientListPanel.removeAll();
        doctorListPanel.removeAll();
        slotListPanel.removeAll();

        // Search Patient Panel
        JPanel patientSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        patientSearchPanel.add(new JLabel("Search Patient by Name:"));
        patientSearchField = new JTextField(20);
        patientSearchPanel.add(patientSearchField);
        JButton searchPatientButton = new JButton("Search");
        searchPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatientList(patientSearchField.getText());
            }
        });
        patientSearchPanel.add(searchPatientButton);

        // Search Doctor Panel
        JPanel doctorSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        doctorSearchPanel.add(new JLabel("Search Doctor by Specialization:"));
        String[] specializations = {"Oncologist", "Pediatrician", "ENT", "Cardiologist"};
        JComboBox<String> specializationComboBox = new JComboBox<>(specializations);
        doctorSearchPanel.add(specializationComboBox);
        JButton searchDoctorButton = new JButton("Search");
        searchDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSpecialization = (String) specializationComboBox.getSelectedItem();
                showDoctorList(selectedSpecialization);
            }
        });
        doctorSearchPanel.add(searchDoctorButton);

        // Add Panels to Main Panel
        patientListPanel.setLayout(new BoxLayout(patientListPanel, BoxLayout.Y_AXIS));
        JPanel patientSearchWrapperPanel = new JPanel(new BorderLayout());
        patientSearchWrapperPanel.add(patientSearchPanel, BorderLayout.NORTH);
        patientSearchWrapperPanel.add(Box.createVerticalStrut(10));
        patientSearchWrapperPanel.add(patientListPanel, BorderLayout.CENTER);

        doctorListPanel.setLayout(new BoxLayout(doctorListPanel, BoxLayout.Y_AXIS));
        JPanel doctorSearchWrapperPanel = new JPanel(new BorderLayout());
        doctorSearchWrapperPanel.add(doctorSearchPanel, BorderLayout.NORTH);
        doctorSearchWrapperPanel.add(Box.createVerticalStrut(10));
        doctorSearchWrapperPanel.add(doctorListPanel, BorderLayout.CENTER);

        mainPanel.removeAll();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(patientSearchWrapperPanel);
        mainPanel.add(doctorSearchWrapperPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showPatientList(String searchKeyword) {
        patientListPanel.removeAll();
        patients = searchPatients(searchKeyword);
        ButtonGroup patientButtonGroup = new ButtonGroup();
        for (Patient patient : patients) {
            JRadioButton radioButton = new JRadioButton(patient.getName() + " | " + patient.getAddress() + " | " + patient.getAge());
            patientButtonGroup.add(radioButton);
            patientListPanel.add(radioButton);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showDoctorList(String searchKeyword) {
        doctorListPanel.removeAll();
        doctors = searchDoctors(searchKeyword);
        ButtonGroup doctorButtonGroup = new ButtonGroup();
        for (Doctor doctor : doctors) {
            JRadioButton radioButton = new JRadioButton(doctor.getName() + " | " + doctor.getSpecialization() + " | " + doctor.getWorkingHours());
            doctorButtonGroup.add(radioButton);
            doctorListPanel.add(radioButton);
        }
        JButton proceedButton = new JButton("Select Doctor");
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor selectedDoctor = getSelectedDoctor(doctorButtonGroup);
                showSlotsForSelectedDoctor(selectedDoctor);
            }
        });
        doctorListPanel.add(proceedButton);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showSlotsForSelectedDoctor(Doctor selectedDoctor) {
        if (selectedDoctor != null) {
            ArrayList<Slot> slots = getSlotsForDoctor(selectedDoctor.getId());
            String[] columnNames = {"Date", "Time"};
            Object[][] data = new Object[slots.size()][2];
            for (int i = 0; i < slots.size(); i++) {
                Slot slot = slots.get(i);
                data[i][0] = slot.getDate();
                data[i][1] = slot.getTime();
            }
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            JButton bookSlotButton = new JButton("Book The Slot");
            bookSlotButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String patientName = getSelectedRadioButtonText(patientListPanel);
                        if (patientName != null) {
                            String patientId = getPatientId(patientName);
                            String date = (String) table.getValueAt(selectedRow, 0);
                            String time = (String) table.getValueAt(selectedRow, 1);
                            String doctorName = selectedDoctor.getName();
                            String doctorId = String.valueOf(selectedDoctor.getId());
                            bookSlot(patientName, patientId, date, time, doctorName, doctorId);
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a patient.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a slot.");
                    }
                }
            });
            slotListPanel.removeAll();
            slotListPanel.setLayout(new BorderLayout());
            slotListPanel.add(scrollPane, BorderLayout.CENTER);
            slotListPanel.add(bookSlotButton, BorderLayout.SOUTH);
            mainPanel.add(slotListPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Error: Doctor not found");
        }
    }

    private ArrayList<Slot> getSlotsForDoctor(int doctorId) {
        ArrayList<Slot> slots = new ArrayList<>();
        slots = new SlotsManagement().getSlots(doctorId);
        return slots;
    }

    private ArrayList<Patient> searchPatients(String keyword) {
        ArrayList<Patient> matchedPatients = new PatientInformationManagement().searchPatientByNameFromDB(keyword);
        return matchedPatients;
    }

    private ArrayList<Doctor> searchDoctors(String keyword) {
        ArrayList<Doctor> matchedDoctors = new DoctorInformationManagement().getDoctorsFromDB(keyword.toLowerCase());
        return matchedDoctors;
    }

    private String getSelectedRadioButtonText(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.isSelected()) {
                    return radioButton.getText();
                }
            }
        }
        return null;
    }

    private Doctor getSelectedDoctor(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                String[] parts = button.getText().split(" \\| ");
                String name = parts[0];
                String specialization = parts[1];
                String workingHours = parts[2];
                for (Doctor doctor : doctors) {
                    if (doctor.getName().equals(name) && doctor.getSpecialization().equals(specialization) && doctor.getWorkingHours().equals(workingHours)) {
                        return doctor;
                    }
                }
            }
        }
        return null;
    }

    private String getPatientId(String patientDetail) {
        // Implement logic to retrieve patient ID based on name
        System.out.println(patientDetail);
        String[] parts = patientDetail.split(" \\| ");
        String patientName = parts[0].strip();

        for (Patient patient : patients) {
            System.out.println(patient.getName());

            if (patient.getName().equals(patientName)) {
                return patient.getId() + "";
            }

        }

        return null;
    }

    private void bookSlot(String patientName, String patientId, String date, String time, String doctorName, String doctorId) {
        SlotsManagement slotsManagement = new SlotsManagement();
        slotsManagement.updateSlot(Integer.parseInt(doctorId), Integer.parseInt(patientId), date, time);
        JOptionPane.showMessageDialog(null, "Slot booked for Patient Name: " + patientName +
                ", Patient ID: " + patientId +
                ", Date: " + date +
                ", Time: " + time +
                " with Doctor Name: " + doctorName +
                ", Doctor ID: " + doctorId);
    }

    private void showPatientDetailsForm() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some spacing

        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        gbc.gridy++;
        JTextField addressField = new JTextField(20);
        formPanel.add(addressField, gbc);
        gbc.gridy++;
        JTextField genderField = new JTextField(20);
        formPanel.add(genderField, gbc);
        gbc.gridy++;
        JTextField ageField = new JTextField(20);
        formPanel.add(ageField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String gender = genderField.getText();
                int age = Integer.parseInt(ageField.getText());
                addPatient(name, age, gender, address);
            }
        });
        buttonPanel.add(addPatientButton);


        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void addPatient(String name, int age, String gender, String address) {
        PatientInformationManagement patientInformationManagement = new PatientInformationManagement();
        patientInformationManagement.addPatient(name, age, gender, address);
        JOptionPane.showMessageDialog(null, "Patient added successfully.");

    }

    private void updatePatient(String name, String address, String gender, int age) {
        // Logic to update patient in the database
        JOptionPane.showMessageDialog(null, "Patient updated successfully.");
    }

    private void showDoctorDetailsForm() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some spacing

        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Specialization:"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Working Hours:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameField = new JTextField(15);
        formPanel.add(nameField, gbc);
        gbc.gridy++;
        String[] specializations = {"Oncologist", "Pediatrician", "ENT", "Cardiologist"};
        JComboBox<String> specializationComboBox = new JComboBox<>(specializations);
        formPanel.add(specializationComboBox, gbc);
        gbc.gridy++;
        JTextField workingHoursField = new JTextField(15);
        formPanel.add(workingHoursField, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String specialization = (String) specializationComboBox.getSelectedItem();
                String workingHours = workingHoursField.getText();

                DoctorInformationManagement doctorInformationManagement = new DoctorInformationManagement();
                doctorInformationManagement.addDoctor(name, specialization, workingHours);

                JOptionPane.showMessageDialog(null, "Doctor details saved: \nName: " + name +
                        "\nSpecialization: " + specialization +
                        "\nWorking Hours: " + workingHours);
            }
        });

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private void updateAppointmentStatus() {
        // Retrieve the list of patients who have appointments for today
        // You may need to implement a method to fetch today's appointments
        System.out.println(logged_doctor_id);
        ArrayList<Booked_Patient> todayAppointments = getTodayAppointments(logged_doctor_id);


        // Create a dialog to select the patient
        JPanel updatePanel = new JPanel(new BorderLayout());

        // Display patient list with radio buttons for selection
        JPanel patientListPanel = new JPanel(new GridLayout(todayAppointments.size(), 1));
        ButtonGroup patientButtonGroup = new ButtonGroup();
        for (Booked_Patient bookedPatient : todayAppointments) {
            JRadioButton patientRadioButton = new JRadioButton(bookedPatient.getName() + " | " + bookedPatient.getAge() + " | " + bookedPatient.getGender());
            patientRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Set the selected patient when the radio button is clicked
                    selectedPatient = bookedPatient;
                }
            });
            patientButtonGroup.add(patientRadioButton);
            patientListPanel.add(patientRadioButton);
        }
        JScrollPane scrollPane = new JScrollPane(patientListPanel);

        updatePanel.add(new JLabel("Today's Appointments:"), BorderLayout.NORTH);
        updatePanel.add(scrollPane, BorderLayout.CENTER);

        JTextArea prescriptionTextArea = new JTextArea(20, 40); // 20 rows and 40 columns
        JScrollPane prescriptionScrollPane = new JScrollPane(prescriptionTextArea);

        // Add prescription input field
        JPanel prescriptionPanel = new JPanel();
        prescriptionPanel.add(new JLabel("Prescription:"));
        prescriptionPanel.add(prescriptionScrollPane);


        // Add button to update prescription and mark appointment as done
        JButton updateButton = new JButton("Update Prescription & Mark Appointment Done");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prescription = prescriptionTextArea.getText();
                if (selectedPatient != null && !prescription.isEmpty()) {
                    // Update prescription and mark appointment as done
                   markAppointmentDone(selectedPatient, prescription);
                    JOptionPane.showMessageDialog(null, "Appointment done. Prescription added successfully! ");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a patient and enter prescription details.");
                }
            }
        });
        prescriptionPanel.add(updateButton, BorderLayout.SOUTH);

        // Add the panel to the main panel
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(updatePanel, BorderLayout.NORTH);
        mainPanel.add(prescriptionPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private ArrayList<Booked_Patient> getTodayAppointments(int doctor_id)
    {
        SlotsManagement slotsManagement = new SlotsManagement();
        return  slotsManagement.getSlotsForDoctor(doctor_id);

    }

    private void markAppointmentDone(Booked_Patient patient , String prescription ) {

        patient.setPrescription(prescription);
        SlotsManagement slotsManagement = new SlotsManagement();
        slotsManagement.updateStatus(patient);
    }
}