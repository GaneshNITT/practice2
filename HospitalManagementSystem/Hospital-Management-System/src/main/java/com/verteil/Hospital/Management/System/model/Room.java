package com.verteil.Hospital.Management.System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    private String roomType; // General, ICU, Private
    private String availability; // Available, Occupied

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    public Room() {
    }

    public Room( String roomType, String availability, Patient patient) {
        this.roomType = roomType;
        this.availability = availability;
        this.patient = patient;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                ", availability='" + availability + '\'' +
                ", patient=" + patient +
                '}';
    }
}