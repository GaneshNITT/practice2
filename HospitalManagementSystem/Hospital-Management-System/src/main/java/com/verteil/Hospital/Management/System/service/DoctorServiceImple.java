package com.verteil.Hospital.Management.System.service;

import com.verteil.Hospital.Management.System.model.Doctor;
import com.verteil.Hospital.Management.System.model.Patient;
import com.verteil.Hospital.Management.System.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DoctorServiceImple  {

    @Autowired
    private  DoctorRepository doctorRepository;
    public List<Doctor> getAvailableDoctorsBySpecialization(String specialization) {
        return doctorRepository.findAvailableDoctorsBySpecialization(specialization);
    }


    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Integer doctorId) {
        return doctorRepository.findById(doctorId);
    }

    public Doctor updateDoctor(Integer doctorId, Doctor doctorDetails) {
        return doctorRepository.findById(doctorId).map(doctor -> {
            doctor.setFirstName(doctorDetails.getFirstName());
            doctor.setLastName(doctorDetails.getLastName());
            doctor.setSpecialization(doctorDetails.getSpecialization());
            doctor.setContactNumber(doctorDetails.getContactNumber());
            doctor.setEmail(doctorDetails.getEmail());
            doctor.setAvailability(doctorDetails.isAvailability());
            return doctorRepository.save(doctor);
        }).orElseThrow(() -> new RuntimeException("Doctor not found with ID " + doctorId));
    }

    public void deleteDoctor(Integer doctorId) {
        doctorRepository.deleteById(doctorId);
    }

}
