package com.avelios.hospital.server.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalPatient {
    @EmbeddedId
    private HospitalPatientId id;

    @ManyToOne
    @MapsId("hospitalId")
    private Hospital hospital;

    @ManyToOne
    @MapsId("patientId")
    private Patient patient;
}
