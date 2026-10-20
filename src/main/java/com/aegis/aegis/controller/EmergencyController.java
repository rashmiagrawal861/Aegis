
package com.aegis.aegis.controller;

import com.aegis.aegis.model.EmergencyRequest;
import com.aegis.aegis.repository.EmergencyRequestRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {

    private final EmergencyRequestRepository repository;

    public EmergencyController(EmergencyRequestRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/test")
    public String testEmergency() {
        return "Aegis Emergency Response API is working!";
    }

    @PostMapping
    public EmergencyRequest createEmergency(
            @RequestBody EmergencyRequest request) {
        return repository.save(request);
    }

    @GetMapping
    public List<EmergencyRequest> getAllEmergencies() {
        return repository.findAll();
    }
    
@PutMapping("/{id}/accept")
public EmergencyRequest acceptEmergency(@PathVariable Long id) {
    EmergencyRequest emergency = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Emergency request not found"
        ));

    if (!"REQUESTED".equals(emergency.getStatus())) {
        throw new ResponseStatusException(
            HttpStatus.CONFLICT, "Emergency is no longer pending"
        );
    }

    emergency.setStatus("ACCEPTED");
    return repository.save(emergency);
}

@PutMapping("/{id}/status")
public EmergencyRequest updateEmergencyStatus(
        @PathVariable Long id,
        @RequestParam String status) {

    EmergencyRequest emergency = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Emergency not found"
        ));

    String current = emergency.getStatus();

    boolean valid =
        ("ACCEPTED".equals(current) && "EN_ROUTE".equals(status)) ||
        ("EN_ROUTE".equals(current) && "PATIENT_PICKED_UP".equals(status)) ||
        ("PATIENT_PICKED_UP".equals(current) && "ARRIVED_AT_HOSPITAL".equals(status));

    if (!valid) {
        throw new ResponseStatusException(
            HttpStatus.CONFLICT, "Invalid emergency status transition"
        );
    }

    emergency.setStatus(status);
    return repository.save(emergency);
}

@PutMapping("/{id}/hospital-ready")
public EmergencyRequest markHospitalReady(@PathVariable Long id) {

    EmergencyRequest emergency = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Emergency request not found"
        ));

    if (!"ACCEPTED".equals(emergency.getStatus()) &&
        !"EN_ROUTE".equals(emergency.getStatus()) &&
        !"PATIENT_PICKED_UP".equals(emergency.getStatus())) {

        throw new ResponseStatusException(
            HttpStatus.CONFLICT,
            "Hospital readiness can only be updated for incoming patients"
        );
    }

    emergency.setHospitalStatus("READY");

    return repository.save(emergency);
}


}


