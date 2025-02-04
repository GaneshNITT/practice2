package com.verteil.Hospital.Management.System.service;

import com.verteil.Hospital.Management.System.model.Patient;
import com.verteil.Hospital.Management.System.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImple {

    @Autowired
    PatientRepository patientRepository;
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }


    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Integer patientId) {
        return patientRepository.findById(patientId);
    }

    public Patient updatePatient(Integer patientId, Patient patientDetails) {
        return patientRepository.findById(patientId).map(patient -> {
            patient.setFirstName(patientDetails.getFirstName());
            patient.setLastName(patientDetails.getLastName());
            patient.setGender(patientDetails.getGender());
            patient.setDateOfBirth(patientDetails.getDateOfBirth());
            patient.setContactNumber(patientDetails.getContactNumber());
            patient.setEmail(patientDetails.getEmail());
            patient.setAddress(patientDetails.getAddress());
            patient.setEmergencyContact(patientDetails.getEmergencyContact());
            patient.setInsuranceDetails(patientDetails.getInsuranceDetails());
            return patientRepository.save(patient);
        }).orElseThrow(() -> new RuntimeException("Patient not found with ID " + patientId));
    }

    public void deletePatient(Integer patientId) {
        patientRepository.deleteById(patientId);
    }
}
