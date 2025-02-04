package com.verteil.Hospital.Management.System.repository;

import com.verteil.Hospital.Management.System.model.Doctor;
import com.verteil.Hospital.Management.System.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor , Integer> {
    @Query(value = "SELECT p.* FROM patient p " +
            "JOIN appointment a ON a.patient_id = p.id " +
            "JOIN doctor d ON a.doctor_id = d.id " +
            "WHERE d.doctorId = :id", nativeQuery = true)
    List<Patient> patientTreatByDr(@Param("specialization") int id);

    @Query("SELECT d FROM Doctor d WHERE d.specialization = :specialization AND d.availability = true")
    List<Doctor> findAvailableDoctorsBySpecialization(@Param("specialization") String specialization);
}
