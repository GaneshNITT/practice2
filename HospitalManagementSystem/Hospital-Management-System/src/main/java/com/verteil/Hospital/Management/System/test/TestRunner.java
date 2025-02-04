package com.verteil.Hospital.Management.System.test;

import com.verteil.Hospital.Management.System.model.*;
import com.verteil.Hospital.Management.System.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Transactional
public class TestRunner implements CommandLineRunner {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RoomRepository roomRepository;


    @Override
    public void run(String... args) throws Exception {
       // saveData();
    }

    private void saveData(){
        // Creating Doctors
        Doctor doctor1 = new Doctor("Amit", "Sharma", "Cardiologist", "9876543210", "amit.sharma@hospital.com", true, new ArrayList<>());
        Doctor doctor2 = new Doctor("Neha", "Kapoor", "Dermatologist", "9876509876", "neha.kapoor@hospital.com", true, new ArrayList<>());
        Doctor doctor3 = new Doctor("Arun", "Roy", "ENT", "9876598765", "arun.roy@hospital.com", true, new ArrayList<>());

        // Saving Doctors
        doctorRepository.saveAll(Arrays.asList(doctor1, doctor2, doctor3));

        // Creating Patients
        Patient patient1 = new Patient("Raj", "Verma", "Male", "2005-01-19", "9876541230", "raj.verma@gmail.com", "Mumbai, India", "9876541200", "LIC Insurance", new ArrayList<>(), new ArrayList<>(), null);
        Patient patient2 = new Patient("Priya", "Mehta", "Female", "2000-10-12", "9876598765", "priya.mehta@gmail.com", "Delhi, India", "9876500001", "Apollo Insurance", new ArrayList<>(), new ArrayList<>(), null);
        Patient patient3 = new Patient("Riya", "Sharma", "Female", "1999-11-02", "9876598765", "riya@gmail.com", "Delhi, India", "9876500001", "NA", new ArrayList<>(), new ArrayList<>(), null);

        // Saving Patients
        patientRepository.saveAll(Arrays.asList(patient1, patient2, patient3));

        // Creating Appointments
        Appointment appointment1 = new Appointment(patient1, doctor1, "2024-04-12", "Scheduled", "Routine check-up");
        Appointment appointment2 = new Appointment(patient2, doctor2, "2024-09-12", "Completed", "Skin allergy treatment");
        Appointment appointment3 = new Appointment(patient3, doctor3, "2024-04-12", "Scheduled", "ENT check-up");
        Appointment appointment4 = new Appointment(patient1, doctor1, "2024-06-22", "Scheduled", "Follow-up check-up");

        // Saving Appointments
        appointmentRepository.saveAll(Arrays.asList(appointment1, appointment2, appointment3, appointment4));

        // Creating Billings
        Billing bill1 = new Billing(patient1, appointment1, 50000.00, "Paid", "Cash", "2024-10-12");
        Billing bill2 = new Billing(patient1, appointment4, 3000.00, "Paid", "Card", "2024-06-22");
        Billing bill3 = new Billing(patient2, appointment2, 1400.00, "Pending", "NA", null);
        Billing bill4 = new Billing(patient3, appointment3, 20000.00, "Pending", "NA", null);

        // Saving Billing
        billingRepository.saveAll(Arrays.asList(bill1, bill2, bill3, bill4));

        // Creating Rooms
        Room room1 = new Room("Private", "Occupied", patient1);
        Room room2 = new Room("ICU", "Available", patient2);
        Room room3 = new Room("General", "Available", patient3);

        // Saving Rooms
        roomRepository.saveAll(Arrays.asList(room1, room2, room3));

        // Assigning relationships for Appointments and Billings
        patient1.getAppointments().addAll(Arrays.asList(appointment1, appointment4));
        patient2.getAppointments().add(appointment2);
        patient3.getAppointments().add(appointment3);

        patient1.getBillings().addAll(Arrays.asList(bill1, bill2));
        patient2.getBillings().add(bill3);
        patient3.getBillings().add(bill4);

        patient1.setRoom(room1);
        patient2.setRoom(room2);
        patient3.setRoom(room3);

        // Now manually assign doctors to appointments (no set relationship between Doctor and Patient in entities)
        doctor1.getAppointments().add(appointment1);
        doctor1.getAppointments().add(appointment4);
        doctor2.getAppointments().add(appointment2);
        doctor3.getAppointments().add(appointment3);

        // Saving the updated Doctors and Patients
        doctorRepository.saveAll(Arrays.asList(doctor1, doctor2, doctor3));
        patientRepository.saveAll(Arrays.asList(patient1, patient2, patient3));
    }

}
