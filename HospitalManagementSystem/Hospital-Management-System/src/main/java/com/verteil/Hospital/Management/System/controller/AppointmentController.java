package com.verteil.Hospital.Management.System.controller;

import com.verteil.Hospital.Management.System.model.Appointment;
import com.verteil.Hospital.Management.System.model.Doctor;
import com.verteil.Hospital.Management.System.model.Patient;
import com.verteil.Hospital.Management.System.repository.AppointmentRepository;
import com.verteil.Hospital.Management.System.repository.DoctorRepository;
import com.verteil.Hospital.Management.System.repository.PatientRepository;
import com.verteil.Hospital.Management.System.test.AppointmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ac")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentController(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentRequest appointmentRequest) {

        Optional<Patient> optionalPatient= patientRepository.findById(appointmentRequest.getPatientId());

        Optional<Doctor> optionalDoctor = doctorRepository.findById(appointmentRequest.getDoctorId());

        Patient p = optionalPatient.get();
        Doctor doctor = optionalDoctor.get();

        Appointment appointment = new Appointment(p, doctor, appointmentRequest.getAppointmentDate(),
                appointmentRequest.getStatus(), appointmentRequest.getDescription());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Integer appointmentId, @RequestBody Appointment appointmentDetails) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
            appointment.setStatus(appointmentDetails.getStatus());
            appointment.setDescription(appointmentDetails.getDescription());
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return ResponseEntity.ok(updatedAppointment);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Integer appointmentId) {
        if (appointmentRepository.existsById(appointmentId)) {
            appointmentRepository.deleteById(appointmentId);
            return ResponseEntity.ok("Appointment with ID " + appointmentId + " has been cancelled successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}

