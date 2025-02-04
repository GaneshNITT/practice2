package com.verteil.Hospital.Management.System.repository;

import com.verteil.Hospital.Management.System.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing , Integer> {
    List<Billing> findByPatient_PatientId(Integer patientId);

}
