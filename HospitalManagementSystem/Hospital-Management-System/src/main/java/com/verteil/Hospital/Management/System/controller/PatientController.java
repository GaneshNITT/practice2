package com.verteil.Hospital.Management.System.controller;

import com.verteil.Hospital.Management.System.model.Appointment;
import com.verteil.Hospital.Management.System.model.Doctor;
import com.verteil.Hospital.Management.System.model.Patient;
import com.verteil.Hospital.Management.System.repository.DoctorRepository;
import com.verteil.Hospital.Management.System.repository.PatientRepository;
import com.verteil.Hospital.Management.System.service.AppointmentServiceImple;
import com.verteil.Hospital.Management.System.service.BillingServiceImple;
import com.verteil.Hospital.Management.System.service.DoctorServiceImple;
import com.verteil.Hospital.Management.System.service.PatientServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pc")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientServiceImple patientServiceImple;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorServiceImple doctorServiceImple;
    @Autowired
    private AppointmentServiceImple appointmentServiceImple;

    @Autowired
    private BillingServiceImple billingServiceImple;

    @Autowired
    private PatientServiceImple patientService;


    @PostMapping("/create")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer patientId) {
        Optional<Patient> patient = patientService.getPatientById(patientId);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer patientId, @RequestBody Patient patientDetails) {
        Patient updatedPatient = patientService.updatePatient(patientId, patientDetails);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.ok("Patient with ID " + patientId + " has been deleted successfully.");
    }

    @GetMapping("/bydr/{id}")
    public List<Patient> findPatientByDrId(@PathVariable int id) {
        return doctorRepository.patientTreatByDr(id);
    }



// new flow of project by me

    //1) get addmited into hospital
    @PostMapping("/admit")
    public ResponseEntity<Patient> admitPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientServiceImple.addPatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    //2) check availability of specialized dr
    @GetMapping("/avaidr/{specialization}")
    public ResponseEntity<List<Doctor>> getAvailableDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> availableDoctors = doctorServiceImple.getAvailableDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(availableDoctors);
    }

    //3) book appointment for the patient of that dr
    //4) Room controller

    //5) calculate total billing

    @Autowired
    private BillingServiceImple billingService;

    @GetMapping("/total/{patientId}")
    public ResponseEntity<Double> getTotalBillAmount(@PathVariable Integer patientId) {
        Double totalAmount = billingService.calculateTotalBillAmount(patientId);
        return ResponseEntity.ok(totalAmount);
    }


}

