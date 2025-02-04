package com.verteil.Hospital.Management.System.repository;

import com.verteil.Hospital.Management.System.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface PatientRepository extends JpaRepository<Patient , Integer> {
//    @Query("SELECT p FROM Patient p WHERE p.doctor.id = :doctorId")
//    List<Patient> findPatientsByDoctorId(@Param("doctorId") int doctorId);

    @Query("SELECT p FROM Patient p JOIN p.appointments a WHERE a.doctor.doctorId = :doctorId")
    List<Patient> findPatientsByDoctorId(@Param("doctorId") int doctorId);
}
