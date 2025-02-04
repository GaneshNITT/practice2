package com.verteil.Hospital.Management.System.repository;

import com.verteil.Hospital.Management.System.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment , Integer> {
    List<Appointment> findByPatient_PatientId(Integer patientId);
}
