package com.verteil.Hospital.Management.System.service;

import com.verteil.Hospital.Management.System.model.Appointment;
import com.verteil.Hospital.Management.System.model.Billing;
import com.verteil.Hospital.Management.System.model.Doctor;
import com.verteil.Hospital.Management.System.model.Patient;
import com.verteil.Hospital.Management.System.repository.AppointmentRepository;
import com.verteil.Hospital.Management.System.repository.BillingRepository;
import com.verteil.Hospital.Management.System.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillingServiceImple {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    private static final Map<String, Double> ROOM_CHARGES = new HashMap<>();

    private static final Map<String, Double> DOCTOR_CHARGES = new HashMap<>();

    static {
        ROOM_CHARGES.put("ICU", 5000.0);
        ROOM_CHARGES.put("Private", 3000.0);
        ROOM_CHARGES.put("General", 1000.0);

        DOCTOR_CHARGES.put("Cardiologist", 5000.0);
        DOCTOR_CHARGES.put("Dermatologist", 2000.0);
        DOCTOR_CHARGES.put("ENT", 3000.0);
    }

    public Double calculateTotalBillAmount(Integer patientId) {
        List<Billing> billingList = billingRepository.findByPatient_PatientId(patientId);
        Double totalAmount = 0.0;

        for (Billing billing : billingList) {
            totalAmount += billing.getTotalAmount();
        }

        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient != null) {

            if (patient.getRoom() != null) {
                String roomType = patient.getRoom().getRoomType();
                Double roomCharge = ROOM_CHARGES.getOrDefault(roomType, 0.0);
                totalAmount += roomCharge;
            }

            List<Appointment> appointments = appointmentRepository.findByPatient_PatientId(patientId);
            for (Appointment appointment : appointments) {
                Doctor doctor = appointment.getDoctor();
                if (doctor != null) {
                    String specialization = doctor.getSpecialization();
                    Double doctorCharge = DOCTOR_CHARGES.getOrDefault(specialization, 0.0);
                    totalAmount += doctorCharge;
                }
            }
        }
        return totalAmount;
    }
}
